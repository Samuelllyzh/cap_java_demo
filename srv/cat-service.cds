using Books as myBooks from '../db/data-model';
using addBooks as myAddBooks from '../db/data-model';
using updateBooks as myUpdateBooks from '../db/data-model';
using deleteBooks as myDeleteBooks from '../db/data-model';

service CatalogService @(requires: 'any') {
    entity Books as projection on myBooks;
    entity addBooks as projection on myAddBooks;
    entity updateBooks as projection on myUpdateBooks;
    entity deleteBooks as projection on myDeleteBooks;
}