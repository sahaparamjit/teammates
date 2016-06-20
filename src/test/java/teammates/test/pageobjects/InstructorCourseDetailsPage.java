package teammates.test.pageobjects;

import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import teammates.common.util.Const;

public class InstructorCourseDetailsPage extends AppPage {
    
    @FindBy (id = "button_sortstudentstatus")
    private WebElement sortByStatusIcon;
    
    @FindBy (id = "button_sortstudentname-0")
    private WebElement sortByNameIcon;
    
    @FindBy (id = "button_sortteam-0")
    private WebElement sortByTeamIcon;
    
    @FindBy (id = "button_remind")
    private WebElement remindAllButton;
    
    @FindBy (id = "rename-team-button")
    private WebElement renameTeamButton;
    
    @FindBy (id = "rename-team-save-button")
    private WebElement renameTeamSaveButton;

    @FindBy (id = "new-team-name-input")
    private WebElement newTeamNameInput;
    
    public InstructorCourseDetailsPage(Browser browser) {
        super(browser);
    }

    @Override
    protected boolean containsExpectedPageContents() {
        return getPageSource().contains("<h1>Course Details</h1>");
    }

    public String getCourseId() {
        return browser.driver.findElement(By.id("courseid")).getText();
    }
    
    public String getFirstTeamInTeamAbleToMergeMessage() {
        return browser.driver.findElement(
                By.cssSelector("#team-able-to-merge-message .first-team")).getText();
    }
    
    public String getSecondTeamInTeamAbleToMergeMessage() {
        return browser.driver.findElement(
                By.cssSelector("#team-able-to-merge-message .second-team")).getText();
    }
    
    public String getFirstTeamInTeamUnableToMergeMessage() {
        return browser.driver.findElement(
                By.cssSelector("#team-unable-to-merge-message .first-team")).getText();
    }
    
    public String getSecondTeamInTeamUnableToMergeMessage() {
        return browser.driver.findElement(
                By.cssSelector("#team-unable-to-merge-message .second-team")).getText();
    }

    public InstructorCourseDetailsPage verifyIsCorrectPage(String courseId) {
        assertEquals(courseId, this.getCourseId());
        return this;
    }

    public InstructorCourseDetailsPage sortByStatus() {
        sortByStatusIcon.click();
        return this;
    }

    public InstructorCourseDetailsPage sortByName() {
        sortByNameIcon.click();
        return this;
    }
    
    public InstructorCourseDetailsPage sortByTeam() {
        sortByTeamIcon.click();
        return this;
    }

    public InstructorCourseDetailsPage clickRemindAllAndCancel() {
        clickAndCancel(remindAllButton);
        return this;
    }
    
    public InstructorCourseDetailsPage clickRemindAllAndConfirm() {
        clickAndConfirm(remindAllButton);
        return this;
    }
    
    public InstructorCourseStudentDetailsViewPage clickViewStudent(String studentName) {
        int rowId = getStudentRowId(studentName);
        getViewLink(rowId).click();
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorCourseStudentDetailsViewPage.class);
    }
    
    public InstructorCourseStudentDetailsViewPage clickAddCommentStudent(String studentName) {
        int rowId = getStudentRowId(studentName);
        getAddCommentDropDownLink(rowId).click();
        getAddCommentToStudentLink(rowId).click();
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorCourseStudentDetailsViewPage.class);
    }
    
    public void submitCommentToCourse(String comment) {
        clickAddCommentToCourseButton();
        WebElement commentTextForm = browser.driver.findElement(By.id("commentText"));
        commentTextForm.click();
        commentTextForm.clear();
        commentTextForm.sendKeys(comment);
        browser.driver.findElement(By.id("button_save_comment")).click();
        waitForPageToLoad();
    }
    
    public void clickAddCommentToCourseButton() {
        browser.driver.findElement(By.id("button_add_comment")).click();
    }

    public InstructorCourseStudentDetailsEditPage clickEditStudent(String studentName) {
        int rowId = getStudentRowId(studentName);
        getEditLink(rowId).click();
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorCourseStudentDetailsEditPage.class);
    }
    
    public InstructorStudentRecordsPage clickAllRecordsLink(String studentName) {
        int rowId = getStudentRowId(studentName);
        getAllRecordsLink(rowId).click();
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorStudentRecordsPage.class);
    }
    
    public InstructorCourseDetailsPage clickRemindStudentAndCancel(String studentName) {
        int rowId = getStudentRowId(studentName);
        clickAndCancel(getRemindLink(rowId));
        return this;
    }
    
    public InstructorCourseDetailsPage clickRemindStudentAndConfirm(String studentName) {
        int rowId = getStudentRowId(studentName);
        clickAndConfirm(getRemindLink(rowId));
        return this;
    }
    
    public InstructorCourseDetailsPage clickDeleteAndCancel(String studentName) {
        int rowId = getStudentRowId(studentName);
        clickAndCancel(getDeleteLink(rowId));
        return this;
    }
    
    public InstructorCourseDetailsPage clickDeleteAndConfirm(String studentName) {
        int rowId = getStudentRowId(studentName);
        clickAndConfirm(getDeleteLink(rowId));
        return this;
    }
    
    public void clickRenameTeamButton() {
        renameTeamButton.click();
    }
    
    public void clickRenameTeamSaveButton() {
        renameTeamSaveButton.click();
    }
    
    public void clickRenameTeamSaveButtonAndConfirm() {
        clickRenameTeamSaveButton();
        waitForConfirmationModalAndClickOk();
    }
    
    public void selectTeamToRename(String teamName) {
        Select teamNameSelect = new Select(browser.driver.findElement(By.id("team-select")));
        teamNameSelect.selectByVisibleText(teamName);
    }
    
    public void enterNewTeamName(String teamName) {
        waitForElementVisibility(newTeamNameInput);
        fillTextBox(newTeamNameInput, teamName);
        triggerKeyUp(newTeamNameInput);
    }
    
    public boolean isRenameTeamSaveButtonEnabled() {
        return isElementEnabled("rename-team-save-button");
    }
    
    public boolean isTeamAbleToMergeMessageVisible() {
        return isElementVisible("team-able-to-merge-message");
    }
    
    public boolean isTeamUnableToMergeMessageVisible() {
        return isElementVisible("team-unable-to-merge-message");
    }

    private WebElement getViewLink(int studentNum) {
        WebElement studentRow = browser.driver.findElement(By.id("student-c0." + studentNum));
        return studentRow.findElement(By.cssSelector("td.no-print.align-center > a:nth-child(1)"));
    }
    
    private WebElement getEditLink(int studentNum) {
        WebElement studentRow = browser.driver.findElement(By.id("student-c0." + studentNum));
        return studentRow.findElement(By.cssSelector("td.no-print.align-center > a:nth-child(2)"));
    }
    
    private WebElement getRemindLink(int studentNum) {
        WebElement studentRow = browser.driver.findElement(By.id("student-c0." + studentNum));
        return studentRow.findElement(By.cssSelector("td.no-print.align-center > a:nth-child(3)"));
    }
    
    private WebElement getDeleteLink(int studentNum) {
        WebElement studentRow = browser.driver.findElement(By.id("student-c0." + studentNum));
        WebElement thirdLink = studentRow.findElement(By.cssSelector("td.no-print.align-center > a:nth-child(3)"));
        
        if ("Delete".equals(thirdLink.getText())) {
            return thirdLink;
        }
        return studentRow.findElement(By.cssSelector("td.no-print.align-center > a:nth-child(4)"));
    }
    
    private WebElement getAllRecordsLink(int studentNum) {
        WebElement studentRow = browser.driver.findElement(By.id("student-c0." + studentNum));
        WebElement thirdLink = studentRow.findElement(By.cssSelector("td.no-print.align-center > a:nth-child(4)"));
        
        if ("All Records".equals(thirdLink.getText())) {
            return thirdLink;
        }
        return studentRow.findElement(By.cssSelector("td.no-print.align-center > a:nth-child(5)"));
    }
    
    private WebElement getAddCommentDropDownLink(int studentNum) {
        WebElement studentRow = browser.driver.findElement(By.id("student-c0." + studentNum));
        return studentRow.findElement(By.cssSelector("td.no-print.align-center > div.btn-group > a.dropdown-toggle"));
    }
    
    private WebElement getAddCommentToStudentLink(int studentNum) {
        WebElement studentRow = browser.driver.findElement(By.id("student-c0." + studentNum));
        return studentRow.findElement(By.cssSelector("td.no-print.align-center > div.btn-group > ul.dropdown-menu"
                                                                            + "> li:nth-child(1) > a"));
    }
    
    private int getStudentRowId(String studentName) {
        int studentCount = browser.driver.findElements(By.className("student_row")).size();
        for (int i = 0; i < studentCount; i++) {
            String studentNameInRow = getStudentNameInRow(i);
            if (studentNameInRow.equals(studentName)) {
                return i;
            }
        }
        return -1;
    }

    private String getStudentNameInRow(int studentNum) {
        return browser.driver.findElement(By.id(Const.ParamsNames.STUDENT_NAME + "-c0." + studentNum)).getText();
    }

}
