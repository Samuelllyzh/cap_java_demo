using Books as myBooks from '../db/data-model';
using LogMessage as myLogMessage from '../db/data-model';

service CatalogService @(requires: 'any') {
    // @odata.draft.enabled
    entity Books as projection on myBooks;
    entity LogMessage as projection on myLogMessage;
}