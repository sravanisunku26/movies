Flow of application: 
Playing now - Horizontal recycler view
Most popular - Vertical recycler View with pagination 
Any click on the app will show the detailed information in the next screen
Implementation steps:
---------------------
I have used below components in the implementation process
1.MVVM Architecture
2.LiveData
3.Retrofit
4.Paging

Used Libraries:
--------------------
To download images from url
    implementation "com.squareup.picasso:picasso:2.71828"
To get data from server
    implementation "com.squareup.retrofit2:retrofit:2.4.0"
    implementation "com.squareup.retrofit2:converter-gson:2.4.0"
To implement pagination
    implementation "androidx.paging:paging-runtime:$paging_version" // For Kotlin use paging-runtime-ktx
    // alternatively - without Android dependencies for testing
    testImplementation "androidx.paging:paging-common:$paging_version" // For Kotlin use paging-common-ktx
To Support Multi resoultion:
    implementation 'com.intuit.sdp:sdp-android:1.0.5'
    implementation 'com.intuit.ssp:ssp-android:1.0.6'

Notes:
------------
I have implemented Juint test cases only.


