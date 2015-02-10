create table PUB_Entry (
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	txt VARCHAR(75) null,
	groupId LONG
);