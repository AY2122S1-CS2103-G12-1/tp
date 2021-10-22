---
layout: page
title: Developer Guide
---

* Table of Contents {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
  original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the
[diagrams](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/docs/diagrams/) folder. Refer to the
[_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create
and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called
[`Main`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/address/Main.java) and
[`MainApp`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/address/MainApp.java). It is
responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in
[`Ui.java`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the
[`MainWindow`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in
[`MainWindow.fxml`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** :
[`Logic.java`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddClientCommand`) which is
   executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API
call.

![Interactions Inside the Logic Component for the `delete -c 1` Command](images/DeleteClientSequenceDiagram.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The lifeline for `DeleteClientCommandParser` should end at the destroy marker (X) but due to a
limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddClientCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddClientCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddClientCommandParser`, `DeleteClientCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** :
[`Model.java`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the
`AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag,
instead of each `Person` needing their own `Tag` objects.
<br>
<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** :
[`Storage.java`](https://github.com/AY2122S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in json format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Client/Product Feature

The add feature allows the users to add a new `Client` or `Product` with details into the application. The commands are
composed of a keyword `add` followed by `-c` for adding clients and `-p` for adding products.

The user inputs the command through `MainWindow` of the UI component, which will pass the input string to
`LogicManager`. In `LogicManager`, the `parseCommand` method in `AddressBookParser` will be called, depending on the
command word, the arguments will be used in `AddClientCommandParser` or `AddProductCommandParser` class for parsing.
The `parse` method will return the result as a `Command`, which will be executed in `LogicManager`. After the
execution, data added will be saved to storage.

For `AddClientCommandParser`, a `Model` is needed as it helps to check whether a string representing an `Order` is
valid.

The flow of the sequence diagram would be the same for adding `Products`, but the UI displayed will be different.

![Interactions Inside the Logic Component for the `add -c Ben -pn 98765432` Command](images/AddClientSequenceDiagram.png)

#### Design Considerations

**Aspect : How `add` may be executed**

* **Alternative 1 (current choice)** : User can add either a client or a product at a time
    * Pros : Allows the user to focus on adding a client or product
    * Cons : Might be slow if there are a lot of clients/products to add
* **Alternative 2** : User can add multiple clients or products
    * Pros : Allows the user to add multiple clients or products in one command
    * Cons : Difficult to find a client/product since the command can be very long, in this case, updates will have to
      be done through the`edit` command (requires the user to memorise the IDs)

### Edit Client/Product Feature

This feature allows the users to edit the details of a `Client` or `Product` of their choice. When editing a `Client` or
`Product`, the user is required to enter at least 1 field to edit in the input command.

The user input is first handled and retrieved by `MainWindow` in the UI component before being passed to the
`LogicManager` to execute. First, `LogicManager` will call `AddressBookParser`, which will pass the inputs to
`EditClientCommandParser`, parsing the inputs and returning a `EditClientCommand`. The command will then be executed in
`LogicManager`, returning a `CommandResult`. `StorageManager` will then attempt to save the current state of address
book into local storage. The `CommandResult` will finally be returned to `MainWindow`, which will display feedback of
the `CommandResult` to the user.

The flow of the sequence diagram would be the same for editing `Products`, but the UI displayed will be different.

![Interactions Inside the Logic Component for the `edit -c 1 -n Sora` Command](images/EditClientSequenceDiagram.png)

#### Design Considerations

**Aspect : How `edit` may be executed**

* **Alternative 1 (current choice)** : User can edit either a client or a product at a time
    * Pros : Allows the user to focus on editing a particular client or product
    * Cons : Unable to edit multiple clients or products at the same time
* **Alternative 2** : User can edit multiple clients or products
    * Pros : Saves time if the user wish to edit a field in all clients or products to the same value
    * Cons : More complex code which would lead to higher amount of error

### View Client/Product Feature

This feature allows the users to view the details of the `Client` or `Product` of their choice. When viewing a `Client`,
more details such as `Products` bought before, will be visible to the user. The user input is first handled and
retrieved by `MainWindow` in the UI component before being passed to the `LogicManager` to execute.

First, `LogicManager` will call `AddressBookParser`, which will pass the inputs to `ViewClientCommandParser`, parsing
the inputs and returning a `ViewClientCommand`. The command will then be executed in `LogicManager`, returning a
`CommandResult` which will be returned to the user. The flow of the sequence diagram would be the same for viewing
`Products`, but the UI displayed will be different.

![Interactions Inside the Logic Component for the `view -c 5` Command](images/ViewClientCommandDiagram.png)

#### Design Considerations

**Aspect : How `view` may be executed**

* **Alternative 1 (current choice)** : User can view either a client or product
    * Pros : Allows the user to focus on a particular client or product
    * Cons : Unable to view multiple clients or products
* **Alternative 2** : User can view multiple clients or products
    * Pros : Easier comparisons between clients or products
    * Cons : More complex code which would lead to higher amount of error

### Delete Client/Product Feature

This feature allows the users to delete a `Client` or `Product` of their choice. When deleting a `Client` or
`Product`, the user is required to list all clients/products using the `list -p` or `list -c` command

The user input is first handled and retrieved by `MainWindow` in the UI component before being passed to the
`LogicManager` to execute. First, `LogicManager` will call `AddressBookParser`, which will pass the inputs to
`DeleteClientCommandParser`, parsing the inputs and returning a `DeleteClientCommand` or . The command will then be
executed in `LogicManager`, returning a `CommandResult`. `StorageManager` will then attempt to save the current state of
address book into local storage. The `CommandResult` will finally be returned to `MainWindow`, which will display
feedback of the `CommandResult` to the user.

The flow of the sequence diagram would be the same for editing `Products`, but the UI displayed will be different.

![Interactions Inside the Logic Component for the `delete -c 1` Command](images/DeleteClientSequenceDiagram.png)

#### Design Considerations

**Aspect : How `delete` may be executed**

* **Alternative 1 (current choice)** : User can delete either a client or a product at a time
    * Pros : Allows the user to focus on deleting a particular client or product
    * Cons : Unable to delete multiple clients or products at the same time
* **Alternative 2** : User can delete multiple clients or products
    * Pros : Saves time if the user wish to delete multiple clients or products at the same time
    * Cons : More complex code which would lead to higher amount of error

### Find Client/Product Feature

This feature allows the users to find a `Client` or `Product` based on their `name`.

The user input is first handled and retrieved by `MainWindow` in the UI component before being passed to the
`LogicManager` to execute. First, `LogicManager` will call `AddressBookParser`, which will pass the inputs to
`FindClientCommandParser`, parsing the inputs and returning a `FindClientCommand`. The command will then be executed in
`LogicManager`, returning a `CommandResult`.The `CommandResult` will finally be returned to `MainWindow`, which will
display feedback of the `CommandResult` to the user.

The flow of the sequence diagram would be the same for finding `Products`, but the UI displayed will be different.

![Interactions Inside the Logic Component for the `find -c john` Command](images/FindClientSequenceDiagram.png)

#### Design Considerations

**Aspect : How `find` may be executed**

* **Alternative 1 (current choice)** : User can find a client/product by their name
    * Pros : Allows the user to focus on finding a particular client or product
    * Cons : Unable to find clients or products without name
* **Alternative 2** : User can find clients by their details such as name, address, email, etc.. and products by their
  name, price, etc..
    * Pros : Saves time if the user is unable to remember the exact name of a client/product.
    * Cons : More complex code which would lead to higher amount of error

### Command History Feature

This feature allows the user to navigate to previous commands using `↑` and `↓` keys.

The command histories are stored in an `ArrayList<String>`, as well as with the help of a `Index`. Each time the user
inputs a command, it is stored into the `ArrayList`. `Index` begins at the end of the `ArrayList`. As user inputs `↑`,
previous command will be shown till no more available. `↓` is used to go back to the next command. As user reaches the
last and latest command stored in `ArrayList`, the next `↓` will clear the command input field. At any time, user can
choose to just stop and proceed on to edit or input the current history command.

### \[Proposed\] Undo/Redo Feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls
`Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be
saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls
`Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the
address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the
`undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state,
then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check
if this is the case. If so, it will return an error to the user rather than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a
limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">

:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to
the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses
`Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than
attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as
`list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus,
the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* online seller on e-commerce platforms who have a large client base
* has a need to keep track of the manufacturing and delivery of their products as well as the contact information of
  their customers and business partners
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Sellah is an address book containing the contact information and orders related to the clients
and partners. The information and status of each order can also be easily monitored by the user. This product makes it
easy and convenient to track orders and look for future cooperation.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | download the app               | use it                                                                 |
| `* * *`  | new user                                   | type ‘exit’                    | quit the app immediately                                               |
| `* * *`  | 2nd-time user                              | add a client                   | keep track of the details of a specific client                         |
| `* * *`  | 2nd-time user                              | add a product                  | keep track of the details of a specific product                        |
| `* * * ` | 2nd-time user                              | edit a client                  | edit the details of a specific client                                  |
| `* * * ` | 2nd-time user                              | edit a product                 | edit the details of a specific product                                 |
| `* * * ` | 2nd-time user                              | view a client                  | view the details of a specific client                                  |
| `* * * ` | 2nd-time user                              | view a product                 | view the details of a specific product                                 |
| `* * * ` | 2nd-time user                              | delete a client                | remove client that I no longer need                                    |
| `* * * ` | 2nd-time user                              | delete a product               | remove product that I no longer need                                   |
| `* * `   | 10th-time user                             | list all my clients            | locate a client easily                                                 |
| `* * `   | 10th-time user                             | list all my products           | locate a product easily                                                |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Sellah` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a client/product**

**MSS**

1. User requests to add a new client/product.
2. Sellah adds the client/product.
3. Sellah displays success message and shows the updated list of clients/products.

   Use case ends.

**Extensions**

* 1a. The input parameter(s) is/are invalid.

    * 1a1. Sellah shows an error message.

      Use case resumes at step 1.

* 1b. The command format is incorrect.

    * 1b1. Sellah shows an error message.

      Use case resumes at step 1.

  Use case ends.

**Use case: UC02 - Edit a client/product**

**MSS**

1. User <ins>requests to list clients/products (UC04)</ins>.
2. Sellah shows a list of clients/products.
3. User requests to edit a specific client/product in the list.
4. Sellah edits the client/product.
5. Sellah displays success message and shows the updated list of clients/products.

   Use case ends.

**Extensions**

* 2a. The list is empty.

    * 2a1. Sellah shows an error message.

      Use case ends.

* 3a. The input parameter(s) is/are invalid.

    * 3a1. Sellah shows an error message.

      Use case resumes at step 3.

* 3b. The command format is incorrect.

    * 3b1. Sellah shows an error message.

      Use case resumes at step 3.

**Use case: UC03 - View a client/product**

**MSS**

1. User <ins> requests to list clients/products (UC04) </ins>
2. Sellah shows a list of clients/products.
3. User request to see more information of a specific client/product in the list.
4. Sellah shows the details of the product/item.

User story ends.

**Extensions**

* 2a. The list is empty.

    * 2a1. Sellah shows an error message.

      Use case ends.

* 3a. The input parameter(s) is/are invalid.

    * 3a1. Sellah shows an error message.

      Use case resumes at step 3.

* 3b. The command format is incorrect.

    * 3b1. Sellah shows an error message.

      Use case resumes at step 3.

* 4a. Some optional details are not present.

    * 4a1. Sellah shows a placeholder value at the optional detail.

      Use case ends.

**Use case: UC04 - List all client/product**

**MSS**

1. User requests to list clients/products.
2. Sellah shows a list of clients/products.

   Use case ends.

**Extensions**

* 2a. The list is empty.

    * 2a1. Sellah shows an error message.

      Use case ends.

**Use case: UC05 - Delete a client/product**

**MSS**

1. User <ins>requests to list clients/products (UC04)</ins>.
2. Sellah shows a list of clients/products.
3. User requests to delete a specific client/product in the list.
4. Sellah deletes the client/product.

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The input INDEX is invalid.

    * 3a1. Sellah shows an error message.

      Use case resumes at step 3.

* 3b. The command format is incorrect.

    * 3b1. Sellah shows an error message.

      Use case resumes at step 3.

**Use case: UC06 - Find a client/product**

**MSS**

1. User requests to find a client/product.
2. Sellah finds a list of clients/products.
3. Sellah displays success message and shows the updated list of clients/products.

   Use case ends.

**Extensions**

* 1a. The input parameter is invalid.

    * 1a1. Sellah shows an error message.

      Use case resumes at step 1.

* 1b. The command format is incorrect.

    * 1b1. Sellah shows an error message. Use case resumes at step 1.

* 2a. The list is empty.

    * 2a1. Sellah shows an error message.

      Use case ends.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">

:information_source: **Note:** These instructions only provide a starting point for testers to work on; testers are
expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a client/product

1. Deleting a client while all clients are being shown

    1. Prerequisites: List all clients using the `list -c` command. Multiple clients in the list.

    1. Test case: `delete -c 1`<br>
       Expected: First client is deleted from the list. Details of the deleted client shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete -c 0`<br>
       Expected: No client is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. Deleting a product while all products are being shown

    1. Prerequisites: List all products using the `list -p` command. Multiple products in the list.

    1. Test case: `delete -p 2`<br>
       Expected: Second product is deleted from the list. Details of the deleted product shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete -p 0`<br>
       Expected: No product is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
