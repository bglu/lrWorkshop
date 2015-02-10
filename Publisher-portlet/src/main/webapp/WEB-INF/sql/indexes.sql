create index IX_52D95134 on PUB_Entry (groupId);
create index IX_12B9A0BE on PUB_Entry (uuid_);
create unique index IX_C521C9AC on PUB_Entry (uuid_, groupId);