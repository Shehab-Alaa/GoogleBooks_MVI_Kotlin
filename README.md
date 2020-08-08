# GoogleBooks_MVI_Kotlin (Clean_Architecture)
This repository contains a simple app to hit Sport-Category Google-Books API and show a list of books, that shows book details when items on the list are tapped, also user is able to add books to his favorite list that implements MVI architecture using Hilt, Retrofit, RxJava, RoomDatabase, DataBinding and Navigation Component Applying Clean Code using OOP and SOLID Priciples.

## Animation Part 
Using Facebook ShimmerLayout Animation , Shared Element Transition and RecyclerView-Item Animation

![117337536_220681359266294_4070239216491888377_n](https://user-images.githubusercontent.com/39988066/89719853-c92dd000-d9cc-11ea-8ed5-2da796c59ee0.png)

## The app has following packages:
1. data: It contains all the data accessing and manipulating components.
2. di: Dependency providing classes using Hilt.
3. view: View classes along with their corresponding Presenters.
4. intent: Intent Classes for dealing with view interactions.
5. utils: Utility classes.

## Navigation Component Graph
![Screenshot (1)](https://user-images.githubusercontent.com/39988066/89719762-09408300-d9cc-11ea-8eec-b517fcfd1ec2.png)

## Guide to app architecture
![mvi_cyclic-49d9f8c2d3fe26b7](https://user-images.githubusercontent.com/39988066/89719755-047bcf00-d9cc-11ea-9f79-e17e0ff8eb78.png)
