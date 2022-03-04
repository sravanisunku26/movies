Flow of application:
----------------------------------
Implemented 3 Apis in 2 screens.
1. Playing now api :- Horizontal recycler view
2. Most popular api :- Used pagination to load list of data. This is vertical Recyclerview with list of data
3. Details api :- Any click on the items, will show the detailed information in the next screen
4. Internet connection before API call is checked and Displayed toast messages accordingly. Live internet connection also written in code. Whenever data connection is lost, it will display messages accordingly and views will update once connection available


Implementation steps:
---------------------
I have used below components in the implementation process
1.MVVM Architecture
2.LiveData
3.Retrofit
4.Paging
5.Data Binding

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
For Testing purpose used below libraries:
    testImplementation 'junit:junit:4.13'
    testImplementation "org.mockito:mockito-core:3.3.3"
    androidTestImplementation "org.mockito:mockito-core:3.3.3"
    testImplementation "io.kotlintest:kotlintest:2.0.7"
    testImplementation 'org.powermock:powermock-module-junit4-rule:2.0.0-beta.5'
    testImplementation 'org.powermock:powermock-core:2.0.0-beta.5'
    testImplementation 'org.powermock:powermock-module-junit4:2.0.0-beta.5'
    testImplementation 'org.powermock:powermock-api-mockito2:2.0.0-beta.5'


Notes:
------------
I have implemented Juint test cases and instrumentation test cases also.


