import java.util.Iterator;

public class Tabell <E> implements AbstraktTabell <E>, Iterable<E> {
    private E[] tabel;

    Tabell(int i){
        tabel = (E[]) new Object[i];
    }

    @Override
    public boolean settIn(E e, int i) {
        if (tabel [i] == null){
            tabel[i]=e;
        return true;}
        return false;
    }

    @Override
    public E returner(int i) {
        if (tabel[i]==null){return null;}
        return tabel[i];
    }


    public Iterator<E> iterator()
    {return new TabellIterator();}




class TabellIterator implements Iterator<E>{
    private int teller = 0;
    @Override
    public boolean hasNext() {
        return teller<tabel.length&&tabel[teller]!=null;
    }

    @Override
    public E next() {
        return tabel[teller++];
    }

    @Override
    public void remove() {

    }
}}
