# Tourisme_app
Thwissa is and Android application that encourage tourism In algeria and bringing tourism agencies closer to tourists
## Features
- Give a tourist Guide (Regions, States, Places)
- Recommendation for tourist places
- Preview  of Articles, Photos, Reviews, Nearby Services, Location .
- Share and preview Stories (sharing photos )
- Post trips  (agency)
- Post a question and start a new Discussions 
....
## Architecture 
This project follows the famous MVVM architecture 
![mvvm archi photo](https://user-images.githubusercontent.com/87452175/161861860-2b8d2829-89f6-49d9-83e1-5e46d4d34c45.png)
## Tech Stack
- kotlin and java as programming languages
- Retrofit for data request and response 
- Moshi for parsing JSON
- tbuonomo for dots indicators

## Project Structure

```
├── App.kt
├── data
│   ├── local
│   │   ├── AppDatabase.kt
│   │   ├── Converters.kt
│   │   ├── daos
│   │   │   
│   │   └── entities
│   ├── remote
│   │   ├── api and data from the net
│   │   └── data from the backend
│   │   
│   └── repositories (place where all the data are collected)
│       
├
├── models
│   └── items and data class
├── ui
│   ├── fragments
│   │   ├── home
│   │   │   ├──  view model 
│   │   │   └──  fragment
│   │   ├── news
│   │   │   ├── 
│   │   │   └── 
│   │   ├── discuss
│   │   │   ├── 
│   │   │   └── 
│   │   ├── entertainment
│   │   │   ├── 
│   │   │   └── 
│   │   ├── profile
│   │   │   ├── 
│   │   │   └──
│   │   │  
│   └── adapters
│       ├── 
│       └── 
└── utils
    ├──  useful function 
    ├──  constants    
```
10 directories, 40 files


