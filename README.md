# Humm 🎧

**CS 2114 · Project 2 · Group 35**
Tanvi Bhat, Preston Moore, Amelia Vasquez Rosario

Humm is a Java playlist organizer. It organizes songs; it doesn't play them. You can create playlists, add songs, delete and restore songs and playlists from Recently Deleted, and clear things out, all in a Swing window styled with FlatLaf.

Everything is stored in our own generic `LinkedChain<T>`. One chain class holds both songs and playlists.

## How it fits together

```
User → HummWindow (5 screens) → Library → Playlist → LinkedChain<T>
```

| Class | Job |
|---|---|
| `LinkedChain<T>` | Generic, unordered storage in linked nodes: add, remove, contains, count, size, clear, iterate |
| `Song` | A song's title and artist. Checks its own fields; two songs are equal if title and artist match, ignoring capitals and extra spaces |
| `Playlist` | One playlist: title, description, and two `LinkedChain<Song>` chains (songs and deleted songs) |
| `Library` | The app's logic: two `LinkedChain<Playlist>` chains (active and deleted playlists) |
| `HummWindow` + 5 panels | Draws the screens, reads clicks and typed text, and shows error messages |

`Playlist` and `Library` only use the chain through its public methods (`add`, `remove`, `size`, `clear`, `iterator`). All app logic, like moving items to Recently Deleted, lives in `Playlist` and `Library`, not in the chain.

## Running it

**Requirements:** Java 17 or newer and Maven. In VS Code, install the Extension Pack for Java.

- **Tests:** run `mvn test`, or open the **Testing** panel (flask icon) in VS Code and click run all.
- **App:** run `mvn exec:java` from the project folder (we will package it into a runnable file later). You can also open `HummWindow.java` and click **Run** above `main`.

```
src/main/java/humm/        LinkedChain, Song, Playlist, Library, HummWindow + panels
src/main/resources/fonts/  Comfortaa and Unbounded (the app's two fonts)
src/main/resources/images/ the hummingbird GIFs (if one is missing, a plain button stands in)
src/test/java/humm/        LinkedChainTest, SongTest, PlaylistTest, LibraryTest
```

## Reflection

### What changed from our specification?

| Area | Spec said | What we built | Why |
|---|---|---|---|
| Validation messages (`Song`) | "Keep it to 80 characters or fewer." and "Please use plain text, with no tabs or line breaks." | "The title must be 80 characters or fewer." and "The title can only use plain text, with no tabs or line breaks." | Naming the field tells the user which box to fix. [Confirm, or change Song to match the spec] |
| Where spaces are cleaned | Song and Playlist both trim and shrink extra spaces when they store text | `Playlist` shrinks extra spaces when storing; `Song` only trims the outside and ignores extra spaces inside `equals()` and `hashCode()` | Same result for matching songs: "Blinding  Lights" still equals "Blinding Lights" |
| Test framework | Not specified | JUnit 5 (`@BeforeEach`, `assertThrows`) | Matches our Maven setup |
| Test plan | Song bad-input tests check the error messages | `SongTest` only checks the exact message for a blank title; other cases check that an exception is thrown | Song's messages changed (see above), so we test behavior instead of exact wording |
| Classes and method signatures | Section 6 of the spec | No changes: `Playlist` and `Library` match their spec signatures exactly | |
| Where validation lives | Song and Playlist constructors | No change | |
| GUI layout | The spec described the five screens | Five panels in one window, switched with a `CardLayout`. The add-a-song and new-playlist forms are pop-up cards that open from a hummingbird button, instead of fields that are always showing | Keeps the lists the main thing on screen. [Amelia: confirm against the spec] |
| Restore all (screens) | A Restore all button on the deleted screens | `Playlist.restoreAllSongs()` is built and tested, but neither deleted screen has a Restore all button yet | Not finished (see Next Steps) |

### What changed in the LinkedChain?

Compared with the provided `LinkedChain.java`, we:
- added `implements Iterable<T>` and an `iterator()` method (a private `ChainIterator`), so `Playlist`, `Library` and the screens can loop with for-each without touching nodes;
- changed `remove(T)` to throw `NoSuchElementException` instead of returning false, so a failed delete reaches the screen as a message;
- removed `get(int)`, `addToEnd`, `remove()` and `toArray`, because they assume an order our unordered design doesn't promise.

Head-only `add`, the size counter, the null check on `add`, `contains`, `count`, `clear` and `toString` are unchanged from the provided class.

### What happened to our unknowns?

- **How to make sure duplicate playlists aren't removed by mistake: figured out.** `Playlist` doesn't override `equals()`, so `remove` only matches the exact object. `LibraryTest.testDeletePlaylistSameTitle` proves deleting one "Getting Ready" playlist leaves the other.
- **How to code scrolling frames for the GUI: figured out.** Each list is a column of row panels inside a `JScrollPane`, so the frame stays the same size however many playlists or songs there are.

### What doesn't work yet?

- `Song` says "Please enter a artist." for a blank artist instead of "an artist."
- `Song`'s messages don't match the exact wording in our spec (see the table above).
- There is no Restore all button on either deleted screen yet. `Playlist.restoreAllSongs()` works, and `Library.restoreAllPlaylists()` is not written yet.
- All tests passed at our last run. 

## Next Steps

### What is left to finish the MVP (in order)

| # | Task | Owner |
|---|---|---|
| 1 | Fix "a artist" and make `Song`'s messages match the spec (or update the spec) | Preston |
| 2 | Add `Library.restoreAllPlaylists()` | Tanvi |
| 3 | Add a Restore all button to both deleted screens (songs and playlists), greyed out when nothing is deleted | Amelia |
| 4 | Run the by-hand GUI checklist from spec section 8: deleted items disappear, bad input is rejected with a message, good input shows up | Amelia and Tanvi |
| 5 | Final full test run, demo prep, slides, and write-up | Everyone |

### Stretch goals

| Stretch goal | Decision |
|---|---|
| Restore all songs (`Playlist.restoreAllSongs()`) | **Committed**. The method is built and tested; the screen button is still to do |
| Restore all playlists (`Library.restoreAllPlaylists()`) | **Committed**. New work: the method in `Library`, then a button on the deleted-playlists screen |
| GUI application | **Committed**, now part of the MVP |
| Recently Deleted | **Committed**, now part of the MVP |
| Save and load | **Dropped**. We don't need it for the MVP |

### New tests we will need

| Added work | Tests | Owner |
|---|---|---|
| Song message fix | `SongTest`: blank artist says "Please enter an artist."; 81 characters and a tab give the spec's messages | Tanvi |
| `Library.restoreAllPlaylists()` | Normal: two deleted playlists come back with their songs. Bad input: restore all with nothing deleted doesn't throw | Tanvi |
| Restore all buttons on the screens | By hand: each Restore all button moves every item back and is greyed out when Recently Deleted is empty | Amelia |

## Attribution

| File | Team member(s) | How |
|---|---|---|
| `LinkedChain.java` | Preston | Added an iterator, made remove throw, removed the order-based methods.|
| `LinkedChainTest.java` | Preston | Wrote code then, asked GenAI to check and fix errors, then re-draft, reviewed, rewrote the comments, and ran it. |
| `Song.java` | Preston | Wrote code then, asked GenAI to check and fix errors, then re-draft, reviewed, rewrote the comments, and ran it. |
| `SongTest.java` | Preston | Wrote code then, asked GenAI to check and fix errors, then re-draft, reviewed, rewrote the comments, and ran it. |
| `Playlist.java` | Tanvi |Wrote code then, asked GenAI to check and fix errors, then re-draft, reviewed, rewrote the comments, and ran it. |
| `PlaylistTest.java` | Tanvi | Wrote code then, asked GenAI to check and fix errors, then re-draft, reviewed, rewrote the comments, and ran it. |
| `Library.java` | Tanvi | Wrote code then, asked GenAI to check and fix errors, then re-draft, reviewed, rewrote the comments, and ran it. |
| `LibraryTest.java` | Tanvi | Wrote code then, asked GenAI to check and fix errors, then re-draft, reviewed, rewrote the comments, and ran it. |
| `HummWindow.java` and the five panels | Amelia | Designed the screens, picked the fonts, made the GIFs, and chose the layout by hand. The Java code was generally built with AI, working from those designs. Amelia tested it, ran it, and decided what to change. |
| `pom.xml` / Maven setup | Amelia | Set up the files in their respective folders and sections to ensure everyone could find what they needed, and provided instructions. |
| `README.md` | Everyone | Tanvi set up and filled it out, Amelia revised it and added her contributions within the project, and Preston added his contributions to the project. |

**How we used GenAI:** Our team used GenAI tools while building Humm, and the tool and the way each of us used it varied. The code was generally built with AI help. Amelia's own work was the designs, fonts, GIFs, layout choices, testing, and decisions about what to keep or change with respect to the GUI, as well as overall reviewing all of the files. Both Tanvi and Preston asked GenAI to review their first rought code drafts and improve it, once they did that, they re wrote comments to understand what GenAI provided and fixed any bugs and wrote tests on their own.
