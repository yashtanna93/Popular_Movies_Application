# Udacity Android-NanoDegree Project 1 Popular-Movies-Application
This application is a project that is a part of Udacity's Android Nanodegree Program where we had to create a project which would allow user to navigate various movies that have released.

## API and Libraries used

* [Picasso library](http://square.github.io/picasso/ "Picasso")
* [https://www.themoviedb.org/](https://www.themoviedb.org/ "TMDB API")
	* If you wish run the application please sign up here and add your own api in the [build.gradle](https://github.com/yashtanna93/Udacity_Android-NanoDegree_Project1_Popular-Movies-Application/blob/master/app/build.gradle) file in app directory

## Project Specifications

The app will:
Upon launch, present the user with an grid arrangement of movie posters.
Allow your user to change sort order via a setting:

* The sort order can be by most popular, or by highest-rated
* Allow the user to tap on a movie poster and transition to a details screen with  additional information such as:
	- original title
	- movie poster image thumbnail
	- A plot synopsis (called overview in the api)
	- user rating (called vote_average in the api)
	- release date