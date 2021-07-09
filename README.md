1.	Bookshop System
Create database for a Bookshop System. A bookshop keeps books. A book can have one author and many categories. Each category can be placed on many books. Let's create a class for each main table.
•	Book - id, title (between 1...50 symbols), description (optional, up to 1000 symbols), edition type (NORMAL, PROMO or GOLD), price, copies, release date (optional), age restriction (MINOR, TEEN or ADULT)
•	Author - id, first name (optional) and last name
•	Category - id, name
Assume everything, which is not market (optional), is mandatory.
The final schema of the database should look like that:
 

Seed Data into the Database
Create seedDatabase() method in the ConsoleRunner class. That method will fill records in the database.
Use the provided files (categories.txt, authors.txt, books.txt) and import the data from them.
Example of seedBooks method
     Files.readAllLines(Path.of(RESOURCE_PATH + BOOKS_FILE_NAME))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Author author = authorService.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                    LocalDate releaseDate = LocalDate.parse(data[1],
                                                     DateTimeFormatter.ofPattern("d/M/yyyy"));
                    int copies = Integer.parseInt(data[2]);
                    BigDecimal price = new BigDecimal(data[3]);
                    AgeRestriction ageRestriction = AgeRestriction
                                                    .values()[Integer.parseInt(data[4])];
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                    Set<Category> categories = categoryService.getRandomCategories();


                    Book book = new Book(title, editionType, price, releaseDate,
                            ageRestriction, author, categories, copies);

                    bookRepository.save(book);
                });

Write Queries
Write the following queries that:
1.	Get all books after the year 2000. Print only their titles.
2.	Get all authors with at least one book with release date before 1990. Print their first name and last name.
3.	Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count.	
4.	Get all books from author George Powell, ordered by their release date (descending), then by book title (ascending). Print the book's title, release date and copies.
