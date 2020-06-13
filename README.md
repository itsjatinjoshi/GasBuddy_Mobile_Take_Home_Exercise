# GasBuddy_Mobile_Take_Home_Exercise

-Created the Exercise app to showcase the skills of Android application development.
-in this app, use the 3rd party library from Unsplash,
-some others 3 party library like Glide for images, retrofit for Web services,
simpleArcLoader to show progress-bar, swipeRefeshLayout to load new content from the server.
-JUnit Testing for the check the Status and message from the 3rd Party Library.

-In this application, I created 2 activities, the first activity is MainActivity to display a list of images with titles, 
and the second activity is ImageDetail activity to display the detail about that image.

- in MainActivity, the view groups I used are:

-SwipeRefreshLayout,
-one Toolbar, 
-EditText- for search particular item from the list,
-RecyclerView, to display all the list of images,
-SimpleArcLoaded - to show the progress of loading the list of images,
-TextView to display the message of loading,

-With the help of RecyclerAdapter, I bind the photo_cardview.xml layout file with the recycler view.

-also, implement onClickListener with the RecyclerAdapet, so second activity will open and show the
details about the images.

-In ImageDetail activity, use the 
-ConstraintsLayout, CardView, ImageView, SimpleArcLoader, TextView.

-SimpleArcLoaded - to show the progress of loading the list of images,
-TextView to display the message of loading,
-ImagesView to display the full view of images,


Others functionality :
-Implements some animation on the RecyclerView, in Main activity,
-Hide the EditText in MainActivity and display it when the user clicks on the Vector Icon of Search.
-implement onClickListenr On the image in ImageDetail to maximize the image to full screen.
-Also, hide the card view and image view when clicking on the image.


