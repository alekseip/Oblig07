import java.util.Iterator;

abstract class EnkelResptListe implements Iterable<Resepter>  {
    private Node forste;
    private Node siste;

    EnkelResptListe(){

    }

    public Node getForste (){return forste;}
    public Node getSiste (){return siste;}
    public void setForste (Node n){forste = n;}
    public void setSiste(Node n){siste=n;}
    public void setForsteNeste(Node n){forste.neste=n;}
    public Node getForsteNeste(){return forste.neste;}
    public void setSisteNeste(Node n){siste.neste=n;}

    public Iterator<Resepter> iterator()
    {return new ListeIterator();}

    public class Node {
        Node neste;
        Resepter data;

        public Node(Resepter resepter){
            this.data=resepter;
        }
    }

    public Resepter finn (int i) throws FinnException{
        Node temp=forste;
        if (forste==null){
            throw  new FinnException("ingen liste");
            }
        while(temp.neste!=null){
            if (i==temp.data.getReseptnummer()){
                return temp.data;
            }
            temp=temp.neste;

        }
        throw new FinnException("finnes ikke i listen");

    }




    class ListeIterator implements Iterator<Resepter> {
        Node tmp = forste;
        @Override
        public boolean hasNext() {
            return tmp!=null;
        }

        @Override
        public Resepter next() {
            Resepter denne = tmp.data;
            tmp = tmp.neste;
            return denne;
        }

        @Override
        public void remove() {

        }
}}


