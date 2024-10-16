# Study Flashcard App

## Overview
**Comment:**  
An interactive flashcard app designed to help users study efficiently by creating, organizing, and sharing flashcard sets. The app encourages user engagement through a variety of study modes, community features, and customization options, making it useful for students, professionals, and lifelong learners.

## Description  
This app allows users to create digital flashcards for any subject or topic and organize them into study sets. The app also offers features like spaced repetition, quiz modes, and collaboration, where users can share or study sets created by others. Users can track their progress over time and study using different modes to reinforce their learning. In addition, the app provides performance statistics and recommendations based on user study habits.

## App Evaluation

**Category:** Education / Productivity

**Story:**  
The app allows users to create, customize, and share flashcards for efficient studying. Users can choose between different study modes (flashcards, quizzes, matching games, etc.) and leverage spaced repetition for optimal learning. Collaboration features allow users to study with friends, join groups, and share sets for community learning.

**Market:**  
This app is suitable for students, teachers, professionals, or anyone looking to learn new subjects in a structured and engaging way. It could be used by people preparing for exams, learning a new language, or even training for certifications.

**Habit:**  
The app could be used daily for study sessions. Its features like spaced repetition and progress tracking encourage regular use. Users may engage with the app as frequently as their study schedules demand, making it a core part of their academic routine.

**Scope:**  
Initially, the app will focus on creating, organizing, and studying flashcards. Future expansions could include gamification elements like leaderboards, in-app challenges, or integration with school curriculums or online courses for better learning outcomes.

## Product Spec

### 1. User Stories (Required and Optional)

#### Required Must-have Stories:
- User signs up or logs in to access their personal flashcard sets and preferences.
- Users can create, edit, and delete flashcards and organize them into study sets.
- Study mode options: Flashcard mode, Quiz mode (multiple choice or fill-in-the-blank), and Matching mode (pair terms and definitions).
- Progress tracking with visual statistics (study time, accuracy rate, sets completed).
- Integration of spaced repetition to optimize long-term retention.
- Collaboration mode: Users can share flashcard sets with friends or join study groups.
- Profile pages for each user to track progress and study habits.
- Settings (Accessibility, Notifications, Study preferences).

#### Optional Nice-to-have Stories:
- Customizable flashcard backgrounds, fonts, and colors for personalization.
- Gamification elements like streaks, daily goals, and badges for study achievements.
- Audio input for users to listen to flashcards or quiz themselves verbally.
- Community feature for users to browse and use flashcard sets created by others, with the ability to follow popular creators.
- Timed study sessions with a timer for focused studying.
- Offline mode to allow studying without an internet connection.
- Flashcard set importing and exporting options for easier content sharing.

### 2. Screens

- **Login/Register**  
  User logs in or registers to save and access flashcard sets, and view their profile.

- **Dashboard Screen**  
  Upon logging in, users are presented with their created flashcard sets, study stats, and a button to start a new session.

- **Flashcard Creation Screen**  
  Users can create new flashcards with text, images, or audio. Options to organize cards into study sets and categories.

- **Study Screen**  
  Multiple study modes (Flashcards, Quiz, Matching game) to test knowledge. Spaced repetition prompts for review.

- **Profile Screen**  
  Displays user stats such as total study time, number of flashcard sets, and progress. Settings for customization.

- **Settings Screen**  
  Users can manage app preferences, such as notifications, study reminders, and accessibility features.

- **Explore Screen**  
  Users can search for and explore popular or shared flashcard sets from other users. Option to join study groups.

### 3. Navigation

**Tab Navigation (Tab to Screen):**
- Dashboard
- Flashcard Creation
- Explore (Community sets)
- Profile
- Settings

**Flow Navigation (Screen to Screen):**
- Forced Log-in -> Account creation if no log in exists.
- Dashboard -> Flashcard Creation or Study Mode.
- Explore -> Join study groups or use shared flashcards.
- Profile -> View progress and access stats.
- Settings -> Change notifications, study preferences, and accessibility options.

![Alt Text](path/to/your/image)
![Alt Text](path/to/your/image)

## Schema

### Models

#### FlashcardSet
| Property      | Type            | Description                                            |
|---------------|-----------------|--------------------------------------------------------|
| objectId      | String           | unique id for each flashcard set (default field)        |
| title         | String           | the title of the flashcard set                          |
| description   | String           | a brief description of the flashcard set                |
| author        | Pointer to User  | reference to the creator of the flashcard set           |
| flashcards    | Array            | an array containing individual flashcards               |
| sharedWith    | Array            | an array of users with whom the set is shared           |
| createdAt     | DateTime         | the date when the flashcard set was created (default)   |
| updatedAt     | DateTime         | the date when the flashcard set was last updated (default) |

#### Flashcard
| Property      | Type            | Description                                            |
|---------------|-----------------|--------------------------------------------------------|
| objectId      | String           | unique id for the flashcard (default field)             |
| question      | String           | the question or term on the flashcard                   |
| answer        | String           | the answer or definition on the flashcard               |
| flashcardSet  | Pointer to FlashcardSet | reference to the flashcard set it belongs to        |
| createdAt     | DateTime         | the date when the flashcard was created (default)       |
| updatedAt     | DateTime         | the date when the flashcard was last updated (default)  |

#### User
| Property      | Type            | Description                                            |
|---------------|-----------------|--------------------------------------------------------|
| objectId      | String           | unique id for the user (default field)                 |
| username      | String           | the username of the user                               |
| password      | String           | hashed password for the user                           |
| email         | String           | email address of the user                              |
| profileImage  | File             | profile picture for the user                           |
| studyStats    | Object           | object containing statistics like study time, accuracy rate, etc. |
| createdAt     | DateTime         | the date the user account was created (default)        |
| updatedAt     | DateTime         | the date when the user details were last updated (default) |

---

### Networking

#### List of network requests by screen

---

**Login and Signup Screen**
- (Create/POST) Create a new user account
- (Read/GET) Log in with existing user credentials

**Dashboard Screen**
- (Read/GET) Fetch flashcard sets created by the logged-in user
    ```swift
    let query = PFQuery(className: "FlashcardSet")
    query.whereKey("author", equalTo: currentUser)
    query.order(byDescending: "createdAt")
    query.findObjectsInBackground { (sets: [PFObject]?, error: Error?) in
        if let error = error {
            print(error.localizedDescription)
        } else if let sets = sets {
            print("Successfully retrieved \(sets.count) flashcard sets.")
        }
    }
    ```

---

**Flashcard Management Screen**
- (Create/POST) Create a new flashcard in a flashcard set
- (Update/PUT) Edit an existing flashcard
- (Delete) Remove a flashcard from a flashcard set
    ```swift
    let flashcard = PFObject(className: "Flashcard")
    flashcard["question"] = "What is X?"
    flashcard["answer"] = "X is ..."
    flashcard["flashcardSet"] = flashcardSet
    flashcard.saveInBackground { (success: Bool, error: Error?) in
        if success {
            print("Flashcard saved successfully.")
        } else {
            print(error?.localizedDescription ?? "Error saving flashcard.")
        }
    }
    ```

---

**Study Mode Screen**
- (Read/GET) Fetch all flashcards in a specific flashcard set
    ```swift
    let query = PFQuery(className: "Flashcard")
    query.whereKey("flashcardSet", equalTo: selectedFlashcardSet)
    query.findObjectsInBackground { (flashcards: [PFObject]?, error: Error?) in
        if let error = error {
            print(error.localizedDescription)
        } else if let flashcards = flashcards {
            print("Successfully retrieved \(flashcards.count) flashcards.")
        }
    }
    ```

---

**Progress Tracking Screen**
- (Read/GET) Retrieve user study statistics (e.g., total study time, accuracy rate)
    ```swift
    let query = PFQuery(className: "User")
    query.getObjectInBackground(withId: currentUser.objectId!) { (user: PFObject?, error: Error?) in
        if let error = error {
            print(error.localizedDescription)
        } else if let user = user {
            let stats = user["studyStats"] as? [String: Any]
            print("Study time: \(stats?["studyTime"] ?? 0)")
        }
    }
    ```

---

**Collaboration Screen**
- (Update/PUT) Share a flashcard set with another user
    ```swift
    flashcardSet.addUniqueObject(anotherUser, forKey: "sharedWith")
    flashcardSet.saveInBackground { (success: Bool, error: Error?) in
        if success {
            print("Flashcard set shared successfully.")
        } else {
            print(error?.localizedDescription ?? "Error sharing flashcard set.")
        }
    }
    ```

---

**Profile Screen**
- (Read/GET) Fetch the logged-in user profile
- (Update/PUT) Update user profile (e.g., change profile image, update study stats)

**Settings Screen**
- (Update/PUT) Update user settings such as notifications and accessibility options
