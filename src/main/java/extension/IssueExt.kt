package extension

import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.IssueStatus
import com.taskadapter.redmineapi.bean.IssueStatusFactory

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */

//0 = {IssueStatus@1995} "Status [id=1, name=New, isDefault=true, closed=false]"
//1 = {IssueStatus@1996} "Status [id=2, name=In progress, isDefault=false, closed=false]"
//2 = {IssueStatus@1997} "Status [id=18, name=In review, isDefault=false, closed=false]"
//3 = {IssueStatus@1998} "Status [id=3, name=Resolved, isDefault=false, closed=false]"
//4 = {IssueStatus@1999} "Status [id=11, name=Ready to test, isDefault=false, closed=false]"
//5 = {IssueStatus@2000} "Status [id=12, name=In QA, isDefault=false, closed=false]"
//6 = {IssueStatus@2001} "Status [id=13, name=Reopened, isDefault=false, closed=false]"
//7 = {IssueStatus@2002} "Status [id=5, name=Closed, isDefault=false, closed=true]"
//8 = {IssueStatus@2003} "Status [id=8, name=Duplicate, isDefault=false, closed=true]"
//9 = {IssueStatus@2004} "Status [id=6, name=Rejected, isDefault=false, closed=true]"
//10 = {IssueStatus@2005} "Status [id=7, name=Need feedback, isDefault=false, closed=false]"
//11 = {IssueStatus@2006} "Status [id=4, name=Testing, isDefault=false, closed=false]"
//12 = {IssueStatus@2007} "Status [id=10, name=Can't reproduce, isDefault=false, closed=false]"
//13 = {IssueStatus@2008} "Status [id=9, name=Assign, isDefault=false, closed=false]"
//14 = {IssueStatus@2009} "Status [id=14, name=Estimated, isDefault=false, closed=false]"
//15 = {IssueStatus@2010} "Status [id=15, name=Sent to Customer, isDefault=false, closed=false]"
//16 = {IssueStatus@2011} "Status [id=16, name=Lost, isDefault=false, closed=true]"
//17 = {IssueStatus@2012} "Status [id=17, name=Won, isDefault=false, closed=true]"
//18 = {IssueStatus@2013} "Status [id=19, name=Investigated, isDefault=false, closed=true]"
//19 = {IssueStatus@2014} "Status [id=20, name=Accepted, isDefault=false, closed=true]"
//20 = {IssueStatus@2015} "Status [id=21, name=Acceptance, isDefault=false, closed=false]"
//21 = {IssueStatus@2016} "Status [id=22, name=Warranty, isDefault=false, closed=false]"
//22 = {IssueStatus@2017} "Status [id=24, name=Support, isDefault=false, closed=false]"
//23 = {IssueStatus@2018} "Status [id=23, name=Finished, isDefault=false, closed=false]"
//24 = {IssueStatus@2019} "Status [id=25, name=Paused, isDefault=false, closed=false]"


fun Issue.setStatus(status: IssueStatus) {
    statusId = status.id
    statusName = status.name
}

fun Issue.inProgress() {
    setStatus(IssueStatusFactory.create(2, "In progress"))
}