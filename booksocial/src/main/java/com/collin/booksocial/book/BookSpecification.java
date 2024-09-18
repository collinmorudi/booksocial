package com.collin.booksocial.book;

import org.springframework.data.jpa.domain.Specification;

/**
 * The BookSpecification class provides methods to create specifications for querying Book entities.
 * This class contains static methods that return specific predicate conditions to be used in JPA criteria queries.
 *
 * The current implementation includes a method to generate a specification to fetch books by their owner's ID.
 */
public class BookSpecification {

    /**
     * Creates a specification used to query books based on the owner's ID.
     * This method generates a predicate that compares the owner's ID of a book against the provided value.
     *
     * @param ownerId the ID of the owner to filter books by
     * @return a specification for filtering books by the owner's ID
     */
    public static Specification<Book> withOwnerId(Integer ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
