Thank you for visiting this Fetch exercise repo!  

I wanted to get this done as quickly as possible so that I could hopefully be one of the first to be able to discuss the project and move forward as I am very interested in working for Fetch as soon as possible in the new year.

The instructions for this exercise were as follows:
Write a native Android app in Kotlin or Java that retrieves the data from https://fetch-hiring.s3.amazonaws.com/hiring.json.
Display this list of items to the user based on the following requirements:
Display all the items grouped by "listId"
Sort the results first by "listId" then by "name" when displaying.
Filter out any items where "name" is blank or null.
The final result should be displayed to the user in an easy-to-read list.
Make the project buildable on the latest (non-pre release) tools and supporting the current release mobile OS.

My current implementation should meet your minimum criteria and is based on the following:

- Material3 UI with pull-to-refresh, and state management
- Clean Architecture separation (UI, ViewModel, Repository)
- Error handling and loading states
- Basic HTTP connection for data fetching
- Grouped and sorted display of items per the design requirement

If I were to continue with iterations to make the app more maintainable, scalable and production-ready while handling multiple APIs efficiently, I would have added in the following improvements:

- Dependency Injection (Hilt or Dagger)
- Room Database for data persistence and caching
- Retrofit with OkHttp for networking, which would be great for a suite of api's
- Repository pattern with Flow
- Unit and UI testing with code coverage minimum requirements (for example 80%)
- CI/CD pipeline set up
- Error tracking and analytics
- Pagination for large datasets
- UI enhancements (i.e., scrollbars, search functionality, nicer looking design)

Thank you again for giving me the opportunity to work on this. I look forward to talking with you as soon as possible! 
