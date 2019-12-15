
public class Main{
   
   public static void main(String[] args) {
      
      AlgoritmoDijkstra d = new AlgoritmoDijkstra(8);
      
      d.agregarVertice(0, 1, 111);
      d.agregarVertice(0, 3, 203);
      d.agregarVertice(1, 2, 323);
      d.agregarVertice(2, 7, 299);
      d.agregarVertice(3, 4, 368);
      d.agregarVertice(3, 5, 239);
      d.agregarVertice(4, 5, 299);
      d.agregarVertice(5, 2, 322);
      d.agregarVertice(5, 6, 350);
      d.agregarVertice(6, 7, 352);
      
      System.out.println(
         "\nCada uno de los nodos del grafo representa una ciudad, segun su indice\n" +
         "en el grafo los nodos son:\n\n" +
         "0 - Santander.\n" +
         "1 - Bilbao.\n" +
         "2 - Zaragoza.\n" +
         "3 - Palencia.\n" +
         "4 - Caceres.\n" +
         "5 - Madrid.\n" +
         "6 - Valencia.\n" +
         "7 - Barcelona.\n"
      );
      
      System.out.println(
         "El costo de la ruta mas corta desde Palencia a Barceloan es: " + d.dijkstra(3, 7) + "\n"
      );
      
      System.out.println(
         "La ruta que se siguie es: " + d.reconstruirRuta(3, 7) + "\n"
      );
   }
}

