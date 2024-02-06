using Books as myBooks from '../db/data-model';

service CatalogService {
    entity Books as projection on myBooks;
}