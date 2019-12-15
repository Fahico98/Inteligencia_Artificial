
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AlgoritmoDijkstra{
   
   // Pequeño valor que sirve de referencia para comparar otros valores.
   private static final double EPS = 1e-6;
   
   // Clase vertice que representa los vertices del grafo, existentes entre dos
   // nodos y que tiene asociado cierto costo.
   public static class Vertice{
      
      double costo;
      int desde, hasta;

      public Vertice(int desde, int hasta, double costo){
         this.desde = desde;
         this.hasta = hasta;
         this.costo = costo;
      }
   }

   // Clase Nodo que representa los nodos del grafo y que se visitaran a medida
   // que se ejecuta el algoritmo de Dijktra.
   public static class Nodo {
      int id;
      double valor;

      public Nodo(int id, double valor) {
         this.id = id;
         this.valor = valor;
      }
   }

   private int n;
   private double[] distancia;
   private Integer[] prev;
   private List<List<Vertice>> grafo;
   
   private Comparator<Nodo> comparador = new Comparator<Nodo>(){
         @Override
         public int compare(Nodo nodo1, Nodo nodo2){
            if(Math.abs(nodo1.valor - nodo2.valor) < EPS){
               return 0;
            }
            return(nodo1.valor - nodo2.valor > 0 ? +1 : -1);
         }
      };

   /**
    * Se inicializa la resolucion de problema dandole al grafo un tamaño y un
    * nodo desde el cual iniciar. Se usa el metodo agregarVertice() para agregar 
    * vertices al grafo vacio.
    *
    * @param n - El numero de nodos en el grafo.
    */
   public AlgoritmoDijkstra(int n){
      this.n = n;
      crearGrafoVacio();
   }
   
   public AlgoritmoDijkstra(int n, Comparator<Nodo> comparador) {
      this(n);
      if(comparador == null){
         throw new IllegalArgumentException("El comparador no puede ser null...");
      }
      this.comparador = comparador;
   }

   /**
    * Agrega un vertice con direccion al grafo.
    *
    * @param desde - Indice del nodo donde inicia el vertice.
    * @param hasta - Indice del nodo donde termina el vertice.
    * @param costo - Costo del vertice.
    */
   public void agregarVertice(int desde, int hasta, int costo){
      grafo.get(desde).add(new Vertice(desde, hasta, costo));
   }

   // Se usa este metodo para obtener el grafo en cuestion.
   public List<List<Vertice>> getGrafo(){
      return grafo;
   }

   /**
    * Recosntruye la ruta mas corta de los nodos que se visitan desde "inicio"
    * hasta "fin", con los nodos "inicio" y "fin" incluidos.
    *
    * Retorna una lista con los inidices de los nodos de la ruta mas corta
    * desde "inicio" hasta "fin". Si los nodos "inicio" y "fin" no estan
    * conectados retorna una lista vacia.
    */
   public List<Integer> reconstruirRuta(int inicio, int fin){
      if(fin < 0 || fin >= n){
         throw new IllegalArgumentException("Indice de nodo invalido...");
      }
      if(inicio < 0 || inicio >= n){
         throw new IllegalArgumentException("Indice de nodo invalido...");
      }
      double distancia = dijkstra(inicio, fin);
      List<Integer> ruta = new ArrayList<>();
      if(distancia == Double.POSITIVE_INFINITY){
         return ruta;
      }
      for(Integer a = fin; a != null; a = prev[a]){
         ruta.add(a);
      }
      Collections.reverse(ruta);
      return ruta;
   }

   /*
    * Ejecuata el algoritmo de Dijkstra para un grafo simplemente dirigido para
    * encontrar la ruta mas corta desde el nodo de inicio hasta el nodo final.
    * Si no existen rutas entre el nodo de inicio y el nodo final se retorna el
    * valor de la variable Double.POSITIVE_INFINITY si existe se retorna el
    * costo de la ruta seguida.
    */
   public double dijkstra(int inicio, int fin){
      
      // Arreglo con los valores de las minimas distancias entre cada nodo y el 
      // nodo inicial.
      distancia = new double[n];
      Arrays.fill(distancia, Double.POSITIVE_INFINITY);
      distancia[inicio] = 0;

      // Cola de proiridad que ordena los nodos segun su posibilidad de ser el
      // nodo menos costoso de visitar.
      PriorityQueue<Nodo> pq = new PriorityQueue<>(2 * n, comparador);
      pq.offer(new Nodo(inicio, 0));

      // Arreglo donde se consideran los nodos que ya han sido visitados.
      boolean[] visitado = new boolean[n];
      prev = new Integer[n];

      while(!pq.isEmpty()){
         Nodo nodo = pq.poll();
         visitado[nodo.id] = true;

         // En esta linea de la ejecucion ya se encontro el nodo con la ruta
         // menos costosa, lo demas se puede ignorar.
         if(distancia[nodo.id] < nodo.valor){
            continue;
         }

         List<Vertice> vertices = grafo.get(nodo.id);
         for(int i = 0; i < vertices.size(); i++){
            Vertice v = vertices.get(i);

            // No es posible encontrar una ruta mas corta ya que todos los demas
            // nodos ya has sido visitados.
            if(visitado[v.hasta]){
               continue;
            }

            // Si la nueva distancia calculada es menor que la que ya existia en
            // en el arreglo "distancia" se actualiza su valor.
            double nuevaDistancia = distancia[v.desde] + v.costo;
            if(nuevaDistancia < distancia[v.hasta]){
               prev[v.hasta] = v.desde;
               distancia[v.hasta] = nuevaDistancia;
               pq.offer(new Nodo(v.hasta, distancia[v.hasta]));
            }
         }
         // Una vez que hemos visitado todos los nodos que abarcan desde el nodo
         // final, sabemos que podemos devolver el valor de la distancia mínima
         // al nodo final porque no puede mejorar después de este punto.
         if(nodo.id == fin){
            return distancia[fin];
         }
      }
      // El nodo final es inalcanzable.
      return Double.POSITIVE_INFINITY;
   }

   // Este metodo construye un grafo vacio.
   private void crearGrafoVacio(){
      grafo = new ArrayList<>(n);
      for(int i = 0; i < n; i++){
         grafo.add(new ArrayList<>());
      }
   }
}
