@cds.persistence.exists
entity Books {
  key ID : Integer;
  title  : String;
  stock  : Integer;
}
@cds.persistence.exists
entity addBooks {
  key ID : UUID;
  title  : String;
  stock  : Integer;
}
@cds.persistence.exists
entity updateBooks {
  key ID : Integer;
  title  : String;
  stock  : Integer;
}
@cds.persistence.exists
entity deleteBooks {
  key ID : Integer;
  title  : String;
  stock  : Integer;
}