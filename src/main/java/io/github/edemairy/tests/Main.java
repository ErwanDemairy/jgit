package io.github.edemairy.tests;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class Main {
    public static void main(String[] args) throws GitAPIException, IOException {
        File repoDir = new File("/Users/edemairy/tmp/update-semanticwebimport");
        try (Repository repository = Git.open(repoDir).getRepository()) {
            String branch = repository.getBranch();
            System.out.println("Current branch: " + branch);

            ObjectId head = repository.resolve("HEAD");
            System.out.println(MessageFormat.format("Head {0}", head.getName()));
            try (RevWalk revWalk = new RevWalk(repository)) {
                revWalk.markStart(revWalk.parseCommit(repository.resolve("HEAD")));

                for (RevCommit commit : revWalk) {
                    System.out.println("Commit: " + commit.getId().getName());
                    System.out.println("Author: " + commit.getAuthorIdent().getName());
                    System.out.println("Date: " + commit.getAuthorIdent().getWhen());
                    System.out.println("Message: " + commit.getFullMessage());
                    System.out.println("---------------------------------------------------");
                }
            }
        }
    }
}