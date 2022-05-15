# GitHub Repositories
GitHub repositories is a lightweight application that uses GitHub public apis, using the app you can access most popular repositories and explore them.
The app is consisted of a main screen where repositories are shown and upon clicking them you can see the detail screen which contain extra information about the repository. Also by clicking the owner name in the detail screen you can access the user profile and check their Twitter handle and the list of user repositories.

## Requirements
### Main Screen
- In the main screen, the user can see a list of the 20 most starred repositories.
- For each one is displayed the order number, the repository name, description and author's name.
- When the description is longer than 150 characters, the value is truncated and added ellipses at the end.
- Upon clicking the repository user should be navigated to the repository detail page.

### Detail screen
- In the detail screen is displayed the repository name, owner name, creation date, description and repository url.
- Upon clicking the url hyperlink user should be directed to the repository webpage.
- Upon clicking the owner name user should be navigated to the user screen.

### User screen
- In the user screen is displayed username, Twitter handle and list of user repositories.
- The list of user repositories has the same requirements as the Main screen
- Upon clicking the repository user should be navigated to the repository detail screen.

### Bookmarks
- User can add/remove bookmark repositories from detail screen
- In the main screen user can see whether the repositories are bookmarked or not
- Bookmarking a repositories in the detail screen, coming back to previous page the list should be updated with the bookmark status
- Bookmarks need to be persisted on app session.

## Contribution
You can contribute to this project by reading the code and poiting out/fixing the potential issues in the code itself. You can also implement a new feature or fix a bug or improve the test coverage on the project.
### Features
There are features missing in the project:
- :white_check_mark: User should see the loading indicator while the data is loading.
- :white_check_mark: If network is not available or there is an error user should see a proper error screen with a retry button.

### Bugs
There are bugs that need to be fixed in the project:
- :white_check_mark: After bookmarking a repository, coming back to previous page the list is not updated.
- :white_check_mark: Identify and fix potential memory leaks in the project

### Testing
We want to test different components to make sure they are working properly:
- What should be tested in each components
  - Check text showed correctly
  - Check text alignment is correct
  - Check click functionality works as expected
  - Check loading screen works as expected while data is loading
  - Check error message showed properly
  - Check button click functionality works as expected
  - Check correct drawable loaded to an imageview
  - Check focus functionality works correctly for edit text
  - Check navigation between screens works as expected
  - Check view visibility is correct
  - Check view is placed correctly
  - Scroll functionality works as expected
  - Items loaded properly in list
  - Check if correct color showed in view

- Add test cases to confirm the followings requirements (Testing method is up to the developer):
  - :white_check_mark: Title is shown properly in list
  - :white_check_mark: Description is truncated properly in the list
  - :white_check_mark: Navigation is handled properly between screens
  - :white_check_mark: All the necessary information are fetched properly in the user screen
  - :white_check_mark: Repository is marked as bookmark properly
  - :white_check_mark: List is updated after changing the bookmark status of a repository

## How to contribute
1. Clone (not fork) this repository
2. Create a new branch for your work
3. Do the changes you want
4. Push your branch and changes to a public repository as a standalone repository

**Please do not fork the this repository, and do not raise a pull request to this repo.**