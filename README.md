# Netvies

Netvies - SqlDelight

Note:
if you want to try this project, please use your own API key and place it in the build.gradle(:core)
in the buildConfigField("String", "API_KEY", '"YOUR API KEY HERE"')

<img width="500" alt="Screen Shot 2022-05-24 at 23 53 31" src="https://user-images.githubusercontent.com/69592810/170092188-456fb630-5e3d-4db7-ab2e-0514a96d1cb5.png">

Issue:

There is an issue in this app when using SqlDelight as a PagingSource, i already follow the documentation
but the app always crash when reaching page 5, with error IndexOutOfBounds throwed by OffsetPageSource

Main Screen

<img src="https://user-images.githubusercontent.com/69592810/170092676-9e91a0cb-a13a-4c22-a39d-df23fe3a5df2.png" width="35%" height="35%">
