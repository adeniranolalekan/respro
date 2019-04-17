create table if not exists Result_Class (
classname varchar(25) not null,
year1 varchar(10) not null,
year2 varchar(10) not null
);
create table if not exists ClassArm(
    armId int identity,
    armname varchar(20) not null
);
create table if not exists ResultClass_ClassArm (
resultClass varchar(25) not null,
classArm int not null
);
alter table ResultClass_ClassArm
add foreign key (resultClass) references Result_Class(classname);
alter table ResultClass_ClassArm
add foreign key (classArm) references ClassArm(armId);
create table if not exists Affective(
aid int identity,
     fPunctuality int not null,
     fNeatness int not null,
    fPoliteness int not null,
     fHonesty int not null,
   fCooperation int not null,
   fLeadership int not null,
   fHelping int not null,
    fEmotion int not null,
    fHealth int not null,
    fAttitude int not null,
    fAttentiveness int not null,
    fPerseverance int not null,
    fSpeaking int not null,
     sPunctuality int not null,
     sNeatness int not null,
    sPoliteness int not null,
     sHonesty int not null,
   sCooperation int not null,
   sLeadership int not null,
   sHelping int not null,
    sEmotion int not null,
    sHealth int not null,
    sAttitude int not null,
    sAttentiveness int not null,
    sPerseverance int not null,
    sSpeaking int not null,
     tPunctuality int not null,
     tNeatness int not null,
    tPoliteness int not null,
     tHonesty int not null,
   tCooperation int not null,
   tLeadership int not null,
   tHelping int not null,
    tEmotion int not null,
    tHealth int not null,
    tAttitude int not null,
    tAttentiveness int not null,
    tPerseverance int not null,
    tSpeaking int not null
);
create table if not exists Student(
 studentId int identity ,
    fTotal float not null ,
    fPosition float not null ,
    name varchar(50) not null,
    sex varchar (10) not null,
    fTeacherComment varchar (100) not null,
    fPrincipalComment varchar (100) not null,
    fPercentage float not  null,
   fAttendance int not null,
   sTotal float not null,
    sPosition float not null,
    sTeacherComment varchar (100) not null,
    sPrincipalComment varchar (100) not null,
    sPercentage float not  null,
   sAttendance int not null,
    tTotal float not null ,
    tPosition float not null,
    tTeacherComment varchar (100) not null,
    tPrincipalComment varchar (100) not null,
    tPercentage float not  null,
   tAttendance int not null,
     noOfSubjectOffered int not null
);
create table if not exists Student_Affective (
student int not null,
affective int not null
);
alter table Student_Affective
add foreign key (student) references Student(studentId);
alter table Student_Affective
add foreign key (affective) references Affective(aid);
create table if not exists ClassTeacher(
classTeacherId int identity
);
create table if not exists User(
 UserId int not null,
     username varchar (50) not null,
    password varchar (50) not null,
     profileName varchar (100) not null,
    role varchar (50) not null,
);
create table if not exists User_ClassTeacher(
user int not null,
classTeacher int not null
);
alter table User_ClassTeacher
add foreign key (user) references User(userId);
alter table User_ClassTeacher
add foreign key (classTeacher) references ClassTeacher(classTeacherId);
create table if not exists ClassArm_ClassTeacher(
classArm int not null,
classTeacher int not null
);
alter table ClassArm_ClassTeacher
add foreign key (classArm) references ClassArm(armId);
alter table ClassArm_ClassTeacher
add foreign key (classTeacher) references ClassTeacher(classTeacherId);
create table if not exists Grade(
gradeId int identity,
   rangeLow int not null,
    rangeHigh int not null,
    gradeName int not null,
   Remark varchar(100) not null
);
create table if not exists PComment(
 pCommentId int identity ,
    rangeLow int not null,
    rangeHigh int not null,
    comment int not null
);
create table if not exists Psychomotor(
 pid int identity,
    fHandwriting int not null,
   fVFluency int not null,
   fGames int not null,
    fSports int not null,
    fHandlingTools int not null,
   fDrawPaint int not null,
    fSocialSkills int not null,
    sHandwriting int not null,
   sVFluency int not null,
   sGames int not null,
    sSports int not null,
    sHandlingTools int not null,
   sDrawPaint int not null,
    sSocialSkills int not null,
    tHandwriting int not null,
   tVFluency int not null,
   tGames int not null,
    tSports int not null,
    tHandlingTools int not null,
   tDrawPaint int not null,
    tSocialSkills int not null
);
create table if not exists Student_Psychomotor (
student int not null,
psychomotor int not null
);
alter table Student_Psychomotor
add foreign key (student) references Student(studentId);
alter table Student_Psychomotor
add foreign key (psychomotor) references Psychomotor(pid);
create table if not exists Scoresheet(
  sheetId int identity,

   fca1 float not null,
   fca2 float  not null,
   fca3 float  not null,
    fExam float  not null,
    fTotal float  not null,
   fPosition int  not null,
    fGrade varchar(10) not null,
    fRemark varchar(100) not null,
   cumAvg float  not null,
   position float  not null,
    sca1 float not null,
   sca2 float  not null,
   sca3 float  not null,
    sExam float  not null,
    sTotal float  not null,
   sPosition int  not null,
    sGrade varchar(10) not null,
    sRemark varchar(100) not null,
     tca1 float not null,
   tca2 float  not null,
   tca3 float  not null,
    tExam float  not null,
    tTotal float  not null,
   tPosition int  not null,
    tGrade varchar(10) not null,
    tRemark varchar(100) not null

);
create table if not exists Subject(
 subjectId int identity ,
    subjectName varchar (50) not null,
    fClassAvg float not null,
    sClassAvg float not null,
    tClassAvg float not null
);
create table if not exists Student_Scoresheet (
student int not null,
scoresheet int not null
);
alter table Student_Scoresheet
add foreign key (student) references Student(studentId);
alter table Student_Scoresheet
add foreign key (scoresheet) references Scoresheet(sheetId);
create table if not exists Subject_Scoresheet (
subject int not null,
scoresheet int not null
);
alter table Subject_Scoresheet
add foreign key (subject) references Subject(subjectId);
alter table Subject_Scoresheet
add foreign key (scoresheet) references Scoresheet(sheetId);
create table if not exists Setting(
  settingId int identity,
    Term int not null,
   resultApproved int not null,
    currentSession int not null,
   serverLock int not null,
   session varchar (20) not null,
    firstResumptionDate varchar (20) not null,
   secondResumptionDate varchar (20) not null,
    thirdResumptionDate varchar (20) not null,
    firstOpened varchar (20) not null,
   econdOpened varchar (20) not null,
   thirdOpened varchar (20) not null,
    firstObtainableCA varchar (20) not null,
    getSecondObtainableCA varchar (20) not null,
    getThirdObtainableCA varchar (20) not null,
    username varchar (20) not null,
    password varchar (20) not null,
    adminRole varchar (20) not null,
   adminProfilename varchar (20) not null
);
create table if not exists TComment(
  tCommentId int identity,
    rangeLow int not null,
    rangeHigh int not null,
    comment int not null
);

create table if not exists ClassArm_Student (
classArm int not null,
student int not null
);
alter table ClassArm_Student
add foreign key (classArm) references ClassArm(armId);
alter table ClassArm_Student
add foreign key (student) references Student(studentId);

create table if not exists ClassArm_Subject (
classArm int not null,
subject int not null
);
alter table ClassArm_Subject
add foreign key (classArm) references ClassArm(armId);
alter table ClassArm_Subject
add foreign key (subject) references Subject(subjectId);

create table if not exists ClassArm_TComment (
classArm int not null,
tcomment int not null
);
alter table ClassArm_TComment
add foreign key (classArm) references ClassArm(armId);
alter table ClassArm_TComment
add foreign key (tcomment) references TComment(tCommentId);
create table if not exists SubjectTeacher(
subjectTeacherId int identity
);
create table if not exists User_SubjectTeacher(
user int not null,
subjectTeacher int not null
);
alter table User_SubjectTeacher
add foreign key (user) references User(userId);
alter table User_SubjectTeacher
add foreign key (subjectTeacher) references SubjectTeacher(subjectTeacherId);
