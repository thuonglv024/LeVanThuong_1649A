package algorithm;

import adt.LinkedListADT;
import model.Book;

public class Sort {
    public static void bubbleSortByTitle(LinkedListADT<Book> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getTitle().compareToIgnoreCase(list.get(j + 1).getTitle()) > 0) {
                    list.swap(j, j + 1);
                }
            }
        }
    }

//    public static void bubbleSortByAuthor(LinkedListADT<Book> list) {
//        int n = list.size();
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = 0; j < n - i - 1; j++) {
//                if (list.get(j).getAuthor().compareToIgnoreCase(list.get(j + 1).getAuthor()) > 0) {
//                    list.swap(j, j + 1);
//                }
//            }
//        }
//    }


}
