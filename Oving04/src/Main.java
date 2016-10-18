import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;

import java.util.LinkedList;

/**
 * Created by EliasBrattli on 10/09/2016.
 * Oppgave 1
 */
public class Main {
    private static CircleList<Integer> soldiers = new CircleList<Integer>();
    public static void main(String[] args){
        generateList(10,soldiers);
        findJosephus2(soldiers,3);
    }
    private static void generateList(int size, CircleList<Integer> list){
        for (int i = 0; i < size; i++) {
            list.add(i+1);
            System.out.println(i+1);
        }
    }

    public static void findJosephus(CircleList<Integer> list,int interval) {
        Node<Integer> n = list.getHead();
        Node<Integer> dead;
        while (list.getSize() > 1) {
            for (int i = 0; i < interval; i++) {
                n = n.getNext();
            }
            dead = n;
            n = n.getNext();
            dead = list.remove(dead);
            System.out.println("Drepte: "+dead.getElement());
        }
        System.out.println("Josephous: "+n.getElement());
    }
    //Optimalisering
    public static void findJosephus2(CircleList<Integer> list,int interval) {
        Node<Integer> n = list.getHead();
        while (list.getSize() > 1) {
            for (int i = 0; i < interval-1; i++) {
                n = n.getNext();
            }
            System.out.println("Drepte: "+list.remove(n.getNext()).getElement());
            n = n.getNext();
        }
        System.out.println("Josephous: "+n.getElement());
    }
}

