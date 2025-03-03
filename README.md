# 발도장 (StamPoot)

### 발도장 앱은?

서울의 다양한 지역을 여행하고 여행 일기를 작성해 추억을 회상할 수 있는 앱이에요.  
일기를 게시판에 공유해서 다른 사람들의 일기를 보고 댓글을 남기거나 나만의 멋진 여행지를 자랑할 수도 있어요.

### 기존 발도장에서는

xml Layout을 통한 UI구성과 MVP 아키텍쳐 패턴, Firebase를 통한 간단한 DB구성을 하고있었어요.  
스토어의 커뮤니티 관련 정책 업데이트를 위해 잠시 스토어에서 내려가있는 상태였어요.

### 새로운 발도장에서는

Jetpack Compose를 통한 새로운 UI 구성, MVVM 패턴을 활용해 유지보수성과 가독성이 좋은 코드를 작성할거에요.  
DB에 직접 읽고쓰는 방식보다는 Retrofit 라이브러리를 통해 RestfulAPI로 서버와 통신할거에요.  
Hilt 라이브러리를 통해 의존성을 주입하고 클래스의 수명 주기를 관리할거에요.

### 프로젝트 구조
![footstamp_diagram.png](app%2Fsrc%2Fmain%2Fres%2Fdrawable%2Ffootstamp_diagram.png)

### 화면별 기능 목록

1. 로그인 Page
    - 앱을 시작할 때 사용할 회원 ID를 생성하고 로그인 하는 기능 (소셜 로그인 Google, Kakao)
2. 지도 View
    - 지도를 보고 각 지역의 일기 개수를 볼 수 있는 기능
    - 선택한 지역에서 Staggered Layout으로 앨범을 만드는 기능
    - 일기 사진을 선택하면 해당 일기를 자세하게 볼 수 있는 기능
3. 앨범 View
    - 일기를 작성하고 제목, 위치, 날짜와 사진들을 업로드할 수 있는 기능
    - 작성한 일기를 지역 별로, 시간 별로 확인하고 관리할 수 있는 기능
    - 일기의 사진들을 전체 화면으로 보거나 확대하는 기능
    - 일기를 선택하면 해당 일기의 자세한 정보를 확인할 수 있는 기능
    - 일기를 선택한 상태에서 그 일기를 공개 게시판에 공유 또는 공유 취소할 수 있는 기능
    - 일기를 삭제하는 기능
4. 게시판 View
    - 공유된 다른 사람의 일기를 확인할 수 있는 기능
    - 랜덤 게시판과 랭킹 게시판 두 가지 탭을 확인할 수 있는 기능
    - 공유된 일기에 좋아요 기능을 하는 스탬프를 찍는 기능
    - 일기에 댓글을 작성할 수 있는 기능
    - 부적절한 일기를 공유할 경우에 신고할 수 있는 기능
    - 자신이 작성한 일기를 공유 해제하거나 댓글을 삭제할 수 있는 기능
5. 프로필 View
    - 공개 게시판에 노출될 프로필 사진과 닉네임을 설정할 수 있는 기능
    - 프로필을 변경할 수 있는 기능
    - 내 일기에 작성된 댓글을 확인할 수 있는 알림 기능
    - 로그 아웃 기능
    - 회원 탈퇴 기능

### 개발 명세서

1. 앨범
    - [x] DataStore를 활용해 일기 Model 저장
    - [x] 저장된 일기를 날짜 별 또는 지역 별로 노출
    - [x] 일기를 선택하면 해당 일기를 화면 전체로 확대
    - [x] 사진을 선택하면 해당 사진을 확대
    - [x] 일기를 작성할 때 썸네일을 지정

2. 지도
    - [x] 앨범에 등록된 일기의 사진을 가공해서 노출
    - [x] 지역 선택 시 해당 지역의 일기들을 노출

3. 로그인
    - [x] 구글 로그인 API 연동
    - [x] 카카오 로그인 API 연동 -> 백엔드에서 이메일을 필요로 함에 따라 정책 문제로 삭제 (구현 완료)
    - [x] API 백엔드 연동

4. 프로필
    - [x] 닉네임과 프로필 사진, 인사말을 변경
    - [x] 공유한 일기와 작성한 댓글을 확인 가능한 내 활동
    - [x] 해당 일기나 댓글을 선택하면 게시판의 해당 일기로 이동 -> 기획 변경으로 기능 변경 (회원 정보 조회)
    - [x] 회원 탈퇴시 회원이 작성한 모든 일기와 댓글 삭제

5. 게시판
    - [x] 랜덤 일기 확인에서는 랜덤으로 공유된 일기를 노출
    - [x] 랭킹 일기에서는 많은 추천을 받은 공유 일기를 노출
    - [x] 일기를 선택하면 해당 일기와 댓글을 노출
    - [x] 일기와 댓글에 문제가 있을 때 사유와 함께 신고

## 개발 환경

- Java Version: 17
- minSdk: 31
- targetSdk: 33
- compileSdk: 34
- gradle: Kotlin DSL
- DesignPattern: MVVM
- Auth: Oauth 2.0

## 사용된 라이브러리

#### coroutine

- Kotlin Coroutine

#### UI

- Jetpack Compose
- Material3
- Navigation-Compose
- SplashScreen

#### DI

- Hilt

#### Image

- Glide
- Coil

#### HTTP Connection

- Retrofit
- LoggingInterceptor
- ConverterScalars
- Gson

#### DB

- Room
- DataStore

#### Auth

- Credentials
- GoogleId
- FirebaseAuth
- KakaoLogin

#### Crashlytics
- Firebase-Crashlytics
- Firebase-Analytics

#### Test

- JUnit

## Problem
문제 발생 시 fhemflrh60@gmail.com 연락 바랍니다.