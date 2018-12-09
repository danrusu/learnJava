package advanced.bookStore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static advanced.bookStore.BookGenre.FICTION;

public class BookStoreTest {

    final String notFoundId = "0000";

    String randomBookId;

    BookStore bookStore;

    // initial books
    Book book1;
    String book1Id;
    Book book2;
    String book2Id;
    Book book3;
    String book3Id;

    // books to add
    Book newBook1;
    Book book1Copy;


    @BeforeEach
    public void beforeEach() {

        book1 = new Book(
                "name1",
                "author",
                2000,
                BookGenre.CHILDREN,
                100f);

        book1Id = "1111";

        book2 = new Book(
                "name2",
                "author",
                2000,
                BookGenre.CHILDREN,
                100f);

        book2Id = "1112";

        book3 = new Book(
                "name3",
                "author",
                2000,
                BookGenre.CHILDREN,
                100f);

        book3Id = "1113";


        newBook1 = new Book(
                "newBook1Name",
                "author",
                2000,
                BookGenre.CHILDREN,
                10f);

        book1Copy = new Book(
                "name1",
                "author",
                2000,
                BookGenre.CHILDREN,
                100f);;


        // Random book id
        randomBookId = BookStore.generateBookId();

        // Mocked bookStore
        Map<String, Book> booksMap = new HashMap<>();


        booksMap.put(book1Id, book1);
        booksMap.put(book2Id, book2);

        Map<String, Integer> quantityMap = new HashMap<>();
        quantityMap.put(book1Id, 10);
        quantityMap.put(book2Id, 5);

        bookStore = new BookStore(booksMap, quantityMap);
        // Created a BookStore with 10 book1, 5 book2
    }

    @Nested
    class GenerateBookIdTest {

        @Test
        public void length_test() {

            Assertions.assertEquals(4, randomBookId.length());
            // Assertions.assertEquals(3, randomBookId.length());   // fail
        }


        @Test
        public void contains_digits_only_test() {
            //randomBookId="11a1"; // fail
            Integer.parseInt(randomBookId);
        }
    }


    @Nested
    class IsBookInStoreTest {

        @Test
        public void positive_test() {

            Assertions.assertEquals(
                    true,
                    bookStore.isBookInStore(book1));
        }


        @Test
        public void negative_test() {

            Assertions.assertEquals(
                    false,
                    bookStore.isBookInStore(book3));
        }

        @Test
        public void book_in_store_from_new_object_test() {

            Book newBook1 = new Book(
                    "name2",
                    "author",
                    2000,
                    BookGenre.CHILDREN, 100f);

            Assertions.assertEquals(
                    true,
                    bookStore.isBookInStore(newBook1));
        }

    }


    @Nested
    class GetBookIdTest {

        @Test
        public void existing_book_test() {

            Assertions.assertEquals(
                    book1Id,
                    bookStore.getBookId(book1));
        }


        @Test
        public void non_existing_book_test() {

            Assertions.assertEquals(
                    notFoundId,
                    //"0001", // fail
                    bookStore.getBookId(book3));
        }

    }

    @Nested
    class GetQuantityTest {

        @Test
        public void book_from_store_test() {

            Assertions.assertEquals(
                    10,
                    bookStore.getQuantity(book1));
        }

        @Test
        public void book_not_in_store_test() {

            Book newBookNotInStore = new Book(
                    "newBookNotInStoreName",
                    "author",
                    2000,
                    BookGenre.CHILDREN,
                    10f);

            Assertions.assertEquals(
                    0,
                    bookStore.getQuantity(newBookNotInStore));
        }


    }


    @Nested
    class AddBookTest {

        @Test
        public void add_one_new_book_test() {

            Assertions.assertEquals(false,
                    bookStore.isBookInStore(newBook1));

            bookStore.addBook(newBook1,5);

            Assertions.assertEquals(true,
                    bookStore.isBookInStore(newBook1));

            Assertions.assertEquals(5,
                    bookStore.getQuantity(newBook1));

        }

        @Test
        public void add_book_already_in_store_test() {

            Assertions.assertEquals(true,
                    bookStore.isBookInStore(book1));

            bookStore.addBook(book1Copy, 5);

            Assertions.assertEquals(true,
                    bookStore.isBookInStore(book1Copy));

            Assertions.assertEquals(15,
                    bookStore.getQuantity(book1Copy));
        }
    }


    @Test
    public void bookStringToBookInfo_test(){

        String bookString = "The Hunger Games | Suzanne Collins | 2008 | FICTION | 11.25 $ | 20";

        BookInfo expectedBookInfo = new BookInfo(
                new Book(
                        "The Hunger Games",
                        "Suzanne Collins",
                        2008,
                        FICTION,
                        11.25f),
                20);


        System.out.println(expectedBookInfo);
        System.out.println(BookStore.bookStringToBookInfo(bookString));

       Assertions.assertTrue(
                expectedBookInfo.equals(
                    BookStore.bookStringToBookInfo(bookString)));
    }


    // #4 SEARCHES
    @Nested
    class SearchBookByName {

        @Test
        public void multiple_books_found_test() throws IOException {

            // GIVEN
            // Set up (test inititalization)

            BookStore bookStore = getBookStoreForTest();
            final String bookSearchName = "Twilight";

            System.out.println(bookStore);

            // WHEN
            List<BookInfo> foundBookInfoList = bookStore.searchBookByName(bookSearchName);

            // THEN
            Assertions.assertEquals(
                    2,
                    foundBookInfoList.size());

            for (int i = 0; i < foundBookInfoList.size(); i++) {

                BookInfo currentBookInfo = foundBookInfoList.get(i);


                Assertions.assertEquals(bookSearchName,
                        currentBookInfo.getBook().getName());
            }
        }


        @Test
        public void no_book_found_test() throws IOException {

            BookStore bookStore = getBookStoreForTest();
            final String bookSearchName = "Unknown book";

            // WHEN
            List<BookInfo> foundBookInfoList = bookStore.searchBookByName(bookSearchName);

            // THEN
            Assertions.assertEquals(
                    0,
                    foundBookInfoList.size());
        }


        @Test
        public void empty_store_test() throws IOException {

            BookStore bookStore = new BookStore();
            final String bookSearchName = "Twilight";

            // WHEN
            List<BookInfo> foundBookInfoList = bookStore.searchBookByName(bookSearchName);

            // THEN
            Assertions.assertEquals(
                    0,
                    foundBookInfoList.size());
        }
    }

    private BookStore getBookStoreForTest() throws IOException {

        BookStore bookStore = new BookStore();

        bookStore.addBook(Paths.get(
                "Resource/Advanced/bookStore/books.txt"));

        final String bookSearchName = "Twilight";

        bookStore.addBook(
                new Book(bookSearchName,
                        "Stephenie Meyer",
                        2018,
                        FICTION,
                        13f),
                40);

        return bookStore;
    }


}
