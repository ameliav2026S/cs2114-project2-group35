# cs2114-project2-group35
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
- **App:** open `HummWindow.java` and click **Run** above `main`.

```
src/main/java/humm/   LinkedChain, Song, Playlist, Library, HummWindow + panels
src/test/java/humm/   LinkedChainTest, SongTest, PlaylistTest, LibraryTest
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
| [GUI changes, e.g. how screens scroll or switch] | | | [Amelia] |

### What changed in the LinkedChain?

Compared with the provided `LinkedChain.java`, we:
- added `implements Iterable<T>` and an `iterator()` method (a private `ChainIterator`), so `Playlist`, `Library` and the screens can loop with for-each without touching nodes;
- changed `remove(T)` to throw `NoSuchElementException` instead of returning false, so a failed delete reaches the screen as a message;
- removed `get(int)`, `addToEnd`, `remove()` and `toArray`, because they assume an order our unordered design doesn't promise.

Head-only `add`, the size counter, the null check on `add`, `contains`, `count`, `clear` and `toString` are unchanged from the provided class.

### What happened to our unknowns?

- **How to make sure duplicate playlists aren't removed by mistake: figured out.** `Playlist` doesn't override `equals()`, so `remove` only matches the exact object. `LibraryTest.testDeletePlaylistSameTitle` proves deleting one "Getting Ready" playlist leaves the other.
- **How to code scrolling frames for the GUI: [figured out / still open].** [Amelia: e.g. each list is inside a JScrollPane]

### What doesn't work yet?

- `Song` says "Please enter a artist." for a blank artist instead of "an artist."
- `Song`'s messages don't match the exact wording in our spec (see the table above).
- [List any unfinished screens, buttons, or failing tests, or write "All tests pass."]

## Next Steps

### What is left to finish the MVP (in order)

| # | Task | Owner |
|---|---|---|
| 1 | Fix "a artist" and make `Song`'s messages match the spec (or update the spec) | Preston |
| 2 | [Finish wiring the screens to `Library` and `Playlist`, e.g. any screen not done yet] | Amelia |
| 3 | Run the by-hand GUI checklist from spec section 8: deleted items disappear, bad input is rejected with a message, good input shows up | Amelia and Tanvi |
| 4 | Final full test run and demo prep | Everyone |

### Stretch goals

| Stretch goal | Decision |
|---|---|
| Restore all songs (`Playlist.restoreAllSongs()`) | **Committed**, already built and tested |
| Restore all playlists (`Library.restoreAllPlaylists()`) | **Committed** [or dropped] |
| GUI application | **Committed**, now part of the MVP |
| Recently Deleted | **Committed**, now part of the MVP |
| Save and load | **Dropped** [or committed] |

### New tests we will need

| Added work | Tests | Owner |
|---|---|---|
| Song message fix | `SongTest`: blank artist says "Please enter an artist."; 81 characters and a tab give the spec's messages | Tanvi |
| `Library.restoreAllPlaylists()` (if committed) | Normal: two deleted playlists come back with their songs. Bad input: restore all with nothing deleted doesn't throw | Tanvi |
| `restoreAllSongs()` on the screen | By hand: Restore all button moves every song back and is greyed out when Recently Deleted is empty | Amelia |
| Save and load (if committed) | Normal: saved playlists reload with songs. Bad input: a missing or broken file doesn't crash | [Owner] |

## Attribution

| File | Team member(s) | How |
|---|---|---|
| `LinkedChain.java` | Preston | [How] |
| `LinkedChainTest.java` | Preston | [How] |
| `Song.java` | Preston | [How] |
| `SongTest.java` | Preston | [How] |
| `Playlist.java` | Tanvi | [How] |
| `PlaylistTest.java` | Tanvi | [How] |
| `Library.java` | Tanvi |  [How] |
| `LibraryTest.java` | Tanvi |  [How] |
| `HummWindow.java` and the five panels | Amelia | [How] |
| `pom.xml` / Maven setup | Amelia | [How] |
| `README.md` | Everyine | [How] |

**How we used GenAI:** ADD
