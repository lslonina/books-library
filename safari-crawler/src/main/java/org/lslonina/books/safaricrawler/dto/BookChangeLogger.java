package org.lslonina.books.safaricrawler.dto;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;

public class BookChangeLogger {
    private static final Logger log = LoggerFactory.getLogger(BookChangeLogger.class);
    public static final Collection<String> IGNORED = asList(
            "/", "/description", "/cover", "/topicsPayload", "/topics",
            "/popularity", "/averageRating", "/reportScore", "/numberOfReviews",
            "/modificationTimestamp", "/updated", "/added");

    public static <T> void logChanges(List<T> books) {
        if (books.size() < 2) {
            throw new RuntimeException("At least two objects required for comparision.");
        }
        T base = books.get(0);
        for (T book : books) {
            if (book != base) {
                logChanges(base, book);
            }
        }
        System.out.println("");
    }

    public static <T> void logChanges(T base, T other) {
        if (base == null) {
            log.info("Base book: " + other);
            return;
        }
        ObjectDiffer objectDiffer = ObjectDifferBuilder.buildDefault();
        DiffNode root = objectDiffer.compare(other, base);

        AtomicInteger counter = new AtomicInteger();
        root.visit((node, visit) -> {
            boolean diff = hasDiff(node);
            if (diff) {
                counter.incrementAndGet();
            }
        });

        if (counter.get() > 0) {
            log.info("Changed book: " + other);
            root.visit((node, visit) -> {
                printDiff(base, other, node);
            });
        }
    }

    private static <T> void printDiff(T baseObject, T other, DiffNode node) {
        String nodeString = node.getPath().toString();
        if (IGNORED.stream().anyMatch(nodeString::equals)) {
            return;
        }
        final Object baseValue = node.canonicalGet(baseObject);
        final Object workingValue = node.canonicalGet(other);
        System.out.println(" " + nodeString);
        System.out.println("  " + baseValue);
        System.out.println("  " + workingValue);
    }

    private static <T> boolean hasDiff(DiffNode node) {
        String nodeString = node.getPath().toString();
        boolean hasOnlyIgnored = IGNORED.stream().noneMatch(nodeString::equals);
        return hasOnlyIgnored;
    }

}
