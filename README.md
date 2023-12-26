# **Description**

---

This project is a simple terminal version of IMDB which is my first big project in **java** and it is done for the purpose of learning and practicing **data model designs**(with use of UML) and **object oriented programing in java**

# Features

---

### **We have 3 types of User Role:**

- Member
- Admin
- Editor
    
    ### Admins:
    
    - Have full access to all data and admin tools
    - Can add, edit, and delete all movies, people, users, reviews, etc.
    - Handle user management like banning or deleting accounts
    - Review and approve/reject edits from editors
    
    ### Editors:
    
    - Can only suggest edits to existing data, not add or delete
    - Suggest edits to things like movie details, trivia, biographies
    - Add missing data like release dates, full cast/crew for older titles
    - Flag inappropriate content for admin review
    - Edit suggestions are queued for admin approval before going live
    - Cannot access admin tools or make major changes without approval
    
    ### Members:
    
    - Rate and review movies out of 5 or 10 stars
    - Write reviews with text, mark spoilers, rate reviews as helpful/not helpful
    - Add movies to customizable personal lists like "Watchlist", "Favorites", "Classics to See" etc.
    - Get recommendations for new movies based on past ratings and favorites
    - Follow people like actors, directors, writers
    - Follow/friend other members and see their reviews, ratings and lists
    - Comment on and discuss movies in forums with other members
    - Contribute trivia, goofs, quotes, soundtrack info for movies
    - Report offensive or inappropriate content to admins
    - Get personalized recommendations based on taste profile
    - See charts of highest rated, most popular movies

### Movies:

- Add movies with titles, plot summaries, posters, trailers etc
- Link movies to actors, directors, writers etc.
- User ratings and reviews
- Recommendation engine to suggest movies to users

### Some General Things

- Movie - > Stores movie info like title, release date, cast, plot summary, ratings, etc.
- Person -> Stores registered users with attributes like name, email, password, date of
birth, etc.
- Director/Actor/Writer -> Stores data on actors, directors, writers, etc. with bio info.
- Review -> User reviews and ratings for movies.
- CustomList -> Custom lists created by users for tracking movies.
- Genre -> Movie genres like comedy, action, drama, etc.
- Role -> Links actors/directors to their roles in movies.
- UserManagement -> Handles user registration, authentication
- MovieDB -> Provides methods(like APIs) to search, retrieve and update Movie entities.
- PersonDB -> Methods(like APIs) to access Person data.
- RecommendationEngine -> Suggests movies based on user profiles and taste.
- SearchComponent -> component for searching movies, people, lists, etc.
- ProfileComponent -> Renders user profile pages with reviews, lists, friends.
- ListManager -> Logic to create and manage user Lists.
- ReviewController -> Logic to manage Review entities like submitting, editing,
    
    modifying.
    

### And more ….

# Notes

---

Since the purpose of this project was for learning more advanced **OOP** and **data model design**, it lacks some error handling and the code can be cleaner and better; Some improvements that can be done are mentioned below:

- Some methods can be more generalized and the class in which they are placed can be changed to a more related one
- Some classes can be merged; for example **Member** and **Editor** can be a single class with an attribute to distinguish them
- In a real world program the data would be saved in a data base or there would be some methods to call some APIs for saving or getting data
