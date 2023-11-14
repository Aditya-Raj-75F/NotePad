package com.example.notepad.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.notepad.EspressoIdlingResource
import com.example.notepad.R
import com.example.notepad.db.TestNoteDatabase
import com.example.notepad.repository.NoteRepository
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@Execution(ExecutionMode.SAME_THREAD)
class MainActivityTest {
//    @JvmField
//    @RegisterExtension
//    val activityScenario = ActivityScenarioExtension.launch<MainActivity>()
    lateinit var activityScenario : ActivityScenario<MainActivity>
    lateinit var db : TestNoteDatabase

    lateinit var viewModel : TestNoteViewModel
//    lateinit var viewModel : NoteViewModel
    @BeforeEach
    fun setup() {
    IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
//    activityScenario.scenario.close()
    activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity {
            val recyclerView = it.findViewById<RecyclerView>(R.id.noteList)
            val adapter = NotesAdapter()
            db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), TestNoteDatabase::class.java).allowMainThreadQueries().build()
            val dao = db.getNotesDao()
            val test_noteRepository = NoteRepository(dao)
            viewModel= TestNoteViewModel(test_noteRepository, it.applicationContext)
//            viewModel = NoteViewModel(it.applicationContext)
            viewModel.getAllNotes().observe(it) { allNotes ->
                adapter.setParameters(allNotes, viewModel)
            }
            recyclerView.adapter = adapter
        }
    Thread.sleep(1000)
    }

    @AfterEach
    fun cleanup() {
//        db.clearAllTables()
//        db.close()
        activityScenario.close()
//        Log.d("USER_TEST","${activityScenario.scenario.moveToState(Lifecycle.State.DESTROYED)}")
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
           }

    @Test
    fun test_isActivityInView(){
        onView(withId(R.id.mainView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isDefaultFragment_noteListFragment() {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_canSwitchToNoteEditableFragment()  {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(withId(R.id.addNoteButton)).perform(click())
    }

    @Test
    fun test_canSwitchBackToNoteListingFragment()  {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(withId(R.id.addNoteButton)).perform(click())
        onView(withId(R.id.saveNoteButton)).perform(click())
    }

    @Test
    fun test_canUseDeleteNoteButton_inNoteEditableFrament() {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(withId(R.id.addNoteButton)).perform(click())
        onView(withId(R.id.deleteButton)).perform(click())
        onView(withText("Delete Confirmation")).check(matches(isDisplayed()))
    }

    @Test
    fun test_completeDeletion_inNoteEditableFragment_withAlertDialogBox() {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(withId(R.id.addNoteButton)).perform(click())
        onView(withId(R.id.deleteButton)).perform(click())
        onView(withText("Yes")).perform(click())
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_cancelDeletion_inNoteEditableFragment_withAlertDialogBox()  {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(withId(R.id.addNoteButton)).perform(click())
        onView(withId(R.id.deleteButton)).perform(click())
        onView(withText("No")).perform(click())
        onView(withId(R.id.saveNoteButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_cardView_inRecyclerView(){
        onView(allOf(
            isDescendantOfA(withId(R.id.noteList)),
            withId(R.id.noteItem),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).check(matches(isDisplayed()))
    }

    @Test
    fun test_isNoteItem_deleteButton_OnClickOperational() {
        onView(allOf(
            isDescendantOfA(allOf(
                withId(R.id.noteItem),
                hasDescendant(allOf(
                    withId(R.id.noteTitleItem),
                    withText("Aditya")
                ))
            )),
            withId(R.id.deleteItemButton)
        )).check(matches(isDisplayed())).perform(click())
    }

    @Test
    fun test_isNoteItem_deleteOperation_alertDialogVisible()  {
        onView(allOf(
            isDescendantOfA(allOf(
                withId(R.id.noteItem),
                hasDescendant(allOf(
                    withId(R.id.noteTitleItem),
                    withText("Aditya")
                ))
            )),
            withId(R.id.deleteItemButton)
        )).check(matches(isDisplayed())).perform(click())
        onView(withText("Delete Confirmation")).check(matches(isDisplayed()))
    }

    @Test
    fun test_noteItemDeletionSuccessful() {
        Thread.sleep(1000)
        onView(withText("Hello")).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.deleteItemButton),
            isDescendantOfA(allOf(
                withId(R.id.noteItem),
                hasDescendant(allOf(
                    withId(R.id.noteTitleItem),
                    withText("Aditya")
                ))
            ))
        )).check(matches(isDisplayed())).perform(click())
        onView(withText("Yes")).perform(click())
        onView(withId(R.id.noteList)).check { view, _ ->
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            val itemCount = adapter?.itemCount ?: 0
            val expectedCount = 1
            assertEquals(expectedCount, itemCount)
        }
    }

    @Test
    fun test_noteItemDeletionCancel() {
        onView(allOf(
            withId(R.id.deleteItemButton),
            isDescendantOfA(allOf(
                withId(R.id.noteItem),
                hasDescendant(allOf(
                    withId(R.id.noteTitleItem),
                    withText("Aditya")
                ))
            ))
        )).check(matches(isDisplayed())).perform(click())
        onView(withText("No")).perform(click())
        onView(withId(R.id.noteList)).check { view, _ ->
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            val itemCount = adapter?.itemCount ?: 0
            val expectedCount = 2
            assertEquals(expectedCount, itemCount)
        }
    }

    @Test
    fun test_editNote_fromNoteItem() {
        onView(allOf(
            isDescendantOfA(allOf(
                withId(R.id.noteItem),
                hasDescendant(allOf(
                    withId(R.id.noteTitleItem),
                    withText("Aditya")
                ))
            )),
            withId(R.id.editNoteButton)
        )).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.editTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.editTitle)).check(matches(withText("Aditya")))
    }

    @Test
    fun test_performUpdate_onNoteItem(){
        onView(allOf(
            isDescendantOfA(allOf(
                withId(R.id.noteItem),
                hasDescendant(allOf(
                    withId(R.id.noteTitleItem),
                    withText("Aditya")
                ))
            )),
            withId(R.id.editNoteButton)
        )).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.editTitle)).perform(typeText("Hello! This is Aditya Raj.")).perform(
            pressBack()
        )
        onView(withId(R.id.editContent)).perform(typeText("It is nice to meet you all.")).perform(
            pressBack()
        )
        onView(withId(R.id.saveNoteButton)).perform(click())
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("AdityaHello! This is Aditya Raj.")
            ))
        )).check(matches(isDisplayed()));
    }

    @Test
    fun test_canToggleBetweenMultipleDelete_andAddNoteButton() {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).perform(longClick());
        onView(withId(R.id.addNoteButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.multiSelectMenuButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).perform(click());
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).perform(click());
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).perform(click());
        onView(withId(R.id.multiSelectMenuButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_canDeselectAllNoteItems_withCancelButton() {
        onView(withId(R.id.noteList)).check(matches(isDisplayed()))
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).perform(longClick());
        onView(withId(R.id.addNoteButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.multiSelectMenuButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).perform(click());
        onView(withId(R.id.cancelSelectedItems)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.multiSelectMenuButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isMultiSelectDeleteButton_displayingDeleteDialogAlert() {
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).perform(longClick());
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).perform(click());
        onView(withId(R.id.multiSelectMenuButton)).perform(click())
        onView(withText("Delete Confirmation")).check(matches(isDisplayed()))
        onView(withText("Yes")).check(matches(isDisplayed())).perform(click())
    }

    @Test
    fun test_isCancel_onDeleteNoteConfirmation_inAlertDialogWorking() {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).perform(longClick());
        onView(withId(R.id.addNoteButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.multiSelectMenuButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).perform(click());
        onView(withId(R.id.multiSelectMenuButton)).perform(click())
        onView(withText("Delete Confirmation")).check(matches(isDisplayed()))
        onView(withText("No")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.cancelSelectedItems)).check(matches(isDisplayed())).perform(click())
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).check(matches(isDisplayed()))
    }

    @Test
    fun test_deleteNotes_onDeleteNoteConfirmation_inAlertDialogWorking(){
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).perform(longClick());
        onView(withId(R.id.addNoteButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.multiSelectMenuButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).perform(click());
        onView(withId(R.id.multiSelectMenuButton)).perform(click())
        onView(withText("Delete Confirmation")).check(matches(isDisplayed()))
        onView(withText("Yes")).check(matches(isDisplayed())).perform(click())
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Aditya")
            ))
        )).check(doesNotExist())
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("Hello")
            ))
        )).check(doesNotExist())
//        Thread.sleep(2000)
    }

    @Test
    fun test_createNewEmptyNote() {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.saveNoteButton)).check(matches(isDisplayed())).perform(click())
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("")
            )),
            hasDescendant(allOf(
                withId(R.id.noteContentItem),
                withText("")
            ))
        )).check(matches(isDisplayed()))
    }

    @Test
    fun test_createNewNote() {
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.editTitle)).check(matches(isDisplayed())).perform(typeText("The Walking Dead")).check(matches(withText("The Walking Dead")))
        onView(withId(R.id.editContent)).check(matches(isDisplayed())).perform(typeText("Rick Grimes\nDaryl Dixon\nMichonne\nMaggie")).check(matches(withText("Rick Grimes\nDaryl Dixon\nMichonne\nMaggie")))
        onView(withId(R.id.saveNoteButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.noteItem),
            isDescendantOfA(withId(R.id.noteList)),
            hasDescendant(allOf(
                withId(R.id.noteTitleItem),
                withText("The Walking Dead")
            )),
            hasDescendant(allOf(
                withId(R.id.noteContentItem),
                withText("Rick Grimes\n" +
                        "Daryl Dixon\n" +
                        "Michonne\n" +
                        "Maggie")
            ))
        )).check(matches(isDisplayed()))

    }
}