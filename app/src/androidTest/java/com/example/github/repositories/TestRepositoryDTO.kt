package com.example.github.repositories

import com.example.github.repositories.data.model.Response
import com.google.gson.Gson

private val testResponseString = "{\n" +
        "  \"total_count\": 79624,\n" +
        "  \"incomplete_results\": false,\n" +
        "  \"items\": [\n" +
        "    {\n" +
        "      \"id\": 5152285,\n" +
        "      \"node_id\": \"MDEwOlJlcG9zaXRvcnk1MTUyMjg1\",\n" +
        "      \"name\": \"okhttp\",\n" +
        "      \"full_name\": \"square/okhttp\",\n" +
        "      \"private\": false,\n" +
        "      \"owner\": {\n" +
        "        \"login\": \"square\",\n" +
        "        \"id\": 82592,\n" +
        "        \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjgyNTky\",\n" +
        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/82592?v=4\",\n" +
        "        \"gravatar_id\": \"\",\n" +
        "        \"url\": \"https://api.github.com/users/square\",\n" +
        "        \"html_url\": \"https://github.com/square\",\n" +
        "        \"followers_url\": \"https://api.github.com/users/square/followers\",\n" +
        "        \"following_url\": \"https://api.github.com/users/square/following{/other_user}\",\n" +
        "        \"gists_url\": \"https://api.github.com/users/square/gists{/gist_id}\",\n" +
        "        \"starred_url\": \"https://api.github.com/users/square/starred{/owner}{/repo}\",\n" +
        "        \"subscriptions_url\": \"https://api.github.com/users/square/subscriptions\",\n" +
        "        \"organizations_url\": \"https://api.github.com/users/square/orgs\",\n" +
        "        \"repos_url\": \"https://api.github.com/users/square/repos\",\n" +
        "        \"events_url\": \"https://api.github.com/users/square/events{/privacy}\",\n" +
        "        \"received_events_url\": \"https://api.github.com/users/square/received_events\",\n" +
        "        \"type\": \"Organization\",\n" +
        "        \"site_admin\": false\n" +
        "      },\n" +
        "      \"html_url\": \"https://github.com/square/okhttp\",\n" +
        "      \"description\": \"Square’s meticulous HTTP client for the JVM, Android, and GraalVM.\",\n" +
        "      \"fork\": false,\n" +
        "      \"url\": \"https://api.github.com/repos/square/okhttp\",\n" +
        "      \"forks_url\": \"https://api.github.com/repos/square/okhttp/forks\",\n" +
        "      \"keys_url\": \"https://api.github.com/repos/square/okhttp/keys{/key_id}\",\n" +
        "      \"collaborators_url\": \"https://api.github.com/repos/square/okhttp/collaborators{/collaborator}\",\n" +
        "      \"teams_url\": \"https://api.github.com/repos/square/okhttp/teams\",\n" +
        "      \"hooks_url\": \"https://api.github.com/repos/square/okhttp/hooks\",\n" +
        "      \"issue_events_url\": \"https://api.github.com/repos/square/okhttp/issues/events{/number}\",\n" +
        "      \"events_url\": \"https://api.github.com/repos/square/okhttp/events\",\n" +
        "      \"assignees_url\": \"https://api.github.com/repos/square/okhttp/assignees{/user}\",\n" +
        "      \"branches_url\": \"https://api.github.com/repos/square/okhttp/branches{/branch}\",\n" +
        "      \"tags_url\": \"https://api.github.com/repos/square/okhttp/tags\",\n" +
        "      \"blobs_url\": \"https://api.github.com/repos/square/okhttp/git/blobs{/sha}\",\n" +
        "      \"git_tags_url\": \"https://api.github.com/repos/square/okhttp/git/tags{/sha}\",\n" +
        "      \"git_refs_url\": \"https://api.github.com/repos/square/okhttp/git/refs{/sha}\",\n" +
        "      \"trees_url\": \"https://api.github.com/repos/square/okhttp/git/trees{/sha}\",\n" +
        "      \"statuses_url\": \"https://api.github.com/repos/square/okhttp/statuses/{sha}\",\n" +
        "      \"languages_url\": \"https://api.github.com/repos/square/okhttp/languages\",\n" +
        "      \"stargazers_url\": \"https://api.github.com/repos/square/okhttp/stargazers\",\n" +
        "      \"contributors_url\": \"https://api.github.com/repos/square/okhttp/contributors\",\n" +
        "      \"subscribers_url\": \"https://api.github.com/repos/square/okhttp/subscribers\",\n" +
        "      \"subscription_url\": \"https://api.github.com/repos/square/okhttp/subscription\",\n" +
        "      \"commits_url\": \"https://api.github.com/repos/square/okhttp/commits{/sha}\",\n" +
        "      \"git_commits_url\": \"https://api.github.com/repos/square/okhttp/git/commits{/sha}\",\n" +
        "      \"comments_url\": \"https://api.github.com/repos/square/okhttp/comments{/number}\",\n" +
        "      \"issue_comment_url\": \"https://api.github.com/repos/square/okhttp/issues/comments{/number}\",\n" +
        "      \"contents_url\": \"https://api.github.com/repos/square/okhttp/contents/{+path}\",\n" +
        "      \"compare_url\": \"https://api.github.com/repos/square/okhttp/compare/{base}...{head}\",\n" +
        "      \"merges_url\": \"https://api.github.com/repos/square/okhttp/merges\",\n" +
        "      \"archive_url\": \"https://api.github.com/repos/square/okhttp/{archive_format}{/ref}\",\n" +
        "      \"downloads_url\": \"https://api.github.com/repos/square/okhttp/downloads\",\n" +
        "      \"issues_url\": \"https://api.github.com/repos/square/okhttp/issues{/number}\",\n" +
        "      \"pulls_url\": \"https://api.github.com/repos/square/okhttp/pulls{/number}\",\n" +
        "      \"milestones_url\": \"https://api.github.com/repos/square/okhttp/milestones{/number}\",\n" +
        "      \"notifications_url\": \"https://api.github.com/repos/square/okhttp/notifications{?since,all,participating}\",\n" +
        "      \"labels_url\": \"https://api.github.com/repos/square/okhttp/labels{/name}\",\n" +
        "      \"releases_url\": \"https://api.github.com/repos/square/okhttp/releases{/id}\",\n" +
        "      \"deployments_url\": \"https://api.github.com/repos/square/okhttp/deployments\",\n" +
        "      \"created_at\": \"2012-07-23T13:42:55Z\",\n" +
        "      \"updated_at\": \"2022-05-14T21:54:02Z\",\n" +
        "      \"pushed_at\": \"2022-05-14T06:40:44Z\",\n" +
        "      \"git_url\": \"git://github.com/square/okhttp.git\",\n" +
        "      \"ssh_url\": \"git@github.com:square/okhttp.git\",\n" +
        "      \"clone_url\": \"https://github.com/square/okhttp.git\",\n" +
        "      \"svn_url\": \"https://github.com/square/okhttp\",\n" +
        "      \"homepage\": \"https://square.github.io/okhttp/\",\n" +
        "      \"size\": 47062,\n" +
        "      \"stargazers_count\": 42128,\n" +
        "      \"watchers_count\": 42128,\n" +
        "      \"language\": \"Kotlin\",\n" +
        "      \"has_issues\": true,\n" +
        "      \"has_projects\": false,\n" +
        "      \"has_downloads\": true,\n" +
        "      \"has_wiki\": false,\n" +
        "      \"has_pages\": true,\n" +
        "      \"forks_count\": 8845,\n" +
        "      \"mirror_url\": null,\n" +
        "      \"archived\": false,\n" +
        "      \"disabled\": false,\n" +
        "      \"open_issues_count\": 137,\n" +
        "      \"license\": {\n" +
        "        \"key\": \"apache-2.0\",\n" +
        "        \"name\": \"Apache License 2.0\",\n" +
        "        \"spdx_id\": \"Apache-2.0\",\n" +
        "        \"url\": \"https://api.github.com/licenses/apache-2.0\",\n" +
        "        \"node_id\": \"MDc6TGljZW5zZTI=\"\n" +
        "      },\n" +
        "      \"allow_forking\": true,\n" +
        "      \"is_template\": false,\n" +
        "      \"topics\": [\n" +
        "        \"android\",\n" +
        "        \"graalvm\",\n" +
        "        \"java\",\n" +
        "        \"kotlin\"\n" +
        "      ],\n" +
        "      \"visibility\": \"public\",\n" +
        "      \"forks\": 8845,\n" +
        "      \"open_issues\": 137,\n" +
        "      \"watchers\": 42128,\n" +
        "      \"default_branch\": \"master\",\n" +
        "      \"score\": 1.0\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 51148780,\n" +
        "      \"node_id\": \"MDEwOlJlcG9zaXRvcnk1MTE0ODc4MA==\",\n" +
        "      \"name\": \"architecture-samples\",\n" +
        "      \"full_name\": \"android/architecture-samples\",\n" +
        "      \"private\": false,\n" +
        "      \"owner\": {\n" +
        "        \"login\": \"android\",\n" +
        "        \"id\": 32689599,\n" +
        "        \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjMyNjg5NTk5\",\n" +
        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/32689599?v=4\",\n" +
        "        \"gravatar_id\": \"\",\n" +
        "        \"url\": \"https://api.github.com/users/android\",\n" +
        "        \"html_url\": \"https://github.com/android\",\n" +
        "        \"followers_url\": \"https://api.github.com/users/android/followers\",\n" +
        "        \"following_url\": \"https://api.github.com/users/android/following{/other_user}\",\n" +
        "        \"gists_url\": \"https://api.github.com/users/android/gists{/gist_id}\",\n" +
        "        \"starred_url\": \"https://api.github.com/users/android/starred{/owner}{/repo}\",\n" +
        "        \"subscriptions_url\": \"https://api.github.com/users/android/subscriptions\",\n" +
        "        \"organizations_url\": \"https://api.github.com/users/android/orgs\",\n" +
        "        \"repos_url\": \"https://api.github.com/users/android/repos\",\n" +
        "        \"events_url\": \"https://api.github.com/users/android/events{/privacy}\",\n" +
        "        \"received_events_url\": \"https://api.github.com/users/android/received_events\",\n" +
        "        \"type\": \"Organization\",\n" +
        "        \"site_admin\": false\n" +
        "      },\n" +
        "      \"html_url\": \"https://github.com/android/architecture-samples\",\n" +
        "      \"description\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris quis efficitur quam. Pellentesque elementum pretium tortor, et placerat dolor varius nec. Etiam viverra, sem ut convallis congue, magna dui blandit eros, quis molestie lorem ex et enim. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla varius tincidunt justo quis cursus. Donec finibus diam a arcu pellentesque tempus. Proin accumsan scelerisque purus, eget vulputate ipsum tempor ac.\",\n" +
        "      \"fork\": false,\n" +
        "      \"url\": \"https://api.github.com/repos/android/architecture-samples\",\n" +
        "      \"forks_url\": \"https://api.github.com/repos/android/architecture-samples/forks\",\n" +
        "      \"keys_url\": \"https://api.github.com/repos/android/architecture-samples/keys{/key_id}\",\n" +
        "      \"collaborators_url\": \"https://api.github.com/repos/android/architecture-samples/collaborators{/collaborator}\",\n" +
        "      \"teams_url\": \"https://api.github.com/repos/android/architecture-samples/teams\",\n" +
        "      \"hooks_url\": \"https://api.github.com/repos/android/architecture-samples/hooks\",\n" +
        "      \"issue_events_url\": \"https://api.github.com/repos/android/architecture-samples/issues/events{/number}\",\n" +
        "      \"events_url\": \"https://api.github.com/repos/android/architecture-samples/events\",\n" +
        "      \"assignees_url\": \"https://api.github.com/repos/android/architecture-samples/assignees{/user}\",\n" +
        "      \"branches_url\": \"https://api.github.com/repos/android/architecture-samples/branches{/branch}\",\n" +
        "      \"tags_url\": \"https://api.github.com/repos/android/architecture-samples/tags\",\n" +
        "      \"blobs_url\": \"https://api.github.com/repos/android/architecture-samples/git/blobs{/sha}\",\n" +
        "      \"git_tags_url\": \"https://api.github.com/repos/android/architecture-samples/git/tags{/sha}\",\n" +
        "      \"git_refs_url\": \"https://api.github.com/repos/android/architecture-samples/git/refs{/sha}\",\n" +
        "      \"trees_url\": \"https://api.github.com/repos/android/architecture-samples/git/trees{/sha}\",\n" +
        "      \"statuses_url\": \"https://api.github.com/repos/android/architecture-samples/statuses/{sha}\",\n" +
        "      \"languages_url\": \"https://api.github.com/repos/android/architecture-samples/languages\",\n" +
        "      \"stargazers_url\": \"https://api.github.com/repos/android/architecture-samples/stargazers\",\n" +
        "      \"contributors_url\": \"https://api.github.com/repos/android/architecture-samples/contributors\",\n" +
        "      \"subscribers_url\": \"https://api.github.com/repos/android/architecture-samples/subscribers\",\n" +
        "      \"subscription_url\": \"https://api.github.com/repos/android/architecture-samples/subscription\",\n" +
        "      \"commits_url\": \"https://api.github.com/repos/android/architecture-samples/commits{/sha}\",\n" +
        "      \"git_commits_url\": \"https://api.github.com/repos/android/architecture-samples/git/commits{/sha}\",\n" +
        "      \"comments_url\": \"https://api.github.com/repos/android/architecture-samples/comments{/number}\",\n" +
        "      \"issue_comment_url\": \"https://api.github.com/repos/android/architecture-samples/issues/comments{/number}\",\n" +
        "      \"contents_url\": \"https://api.github.com/repos/android/architecture-samples/contents/{+path}\",\n" +
        "      \"compare_url\": \"https://api.github.com/repos/android/architecture-samples/compare/{base}...{head}\",\n" +
        "      \"merges_url\": \"https://api.github.com/repos/android/architecture-samples/merges\",\n" +
        "      \"archive_url\": \"https://api.github.com/repos/android/architecture-samples/{archive_format}{/ref}\",\n" +
        "      \"downloads_url\": \"https://api.github.com/repos/android/architecture-samples/downloads\",\n" +
        "      \"issues_url\": \"https://api.github.com/repos/android/architecture-samples/issues{/number}\",\n" +
        "      \"pulls_url\": \"https://api.github.com/repos/android/architecture-samples/pulls{/number}\",\n" +
        "      \"milestones_url\": \"https://api.github.com/repos/android/architecture-samples/milestones{/number}\",\n" +
        "      \"notifications_url\": \"https://api.github.com/repos/android/architecture-samples/notifications{?since,all,participating}\",\n" +
        "      \"labels_url\": \"https://api.github.com/repos/android/architecture-samples/labels{/name}\",\n" +
        "      \"releases_url\": \"https://api.github.com/repos/android/architecture-samples/releases{/id}\",\n" +
        "      \"deployments_url\": \"https://api.github.com/repos/android/architecture-samples/deployments\",\n" +
        "      \"created_at\": \"2016-02-05T13:42:07Z\",\n" +
        "      \"updated_at\": \"2022-05-15T02:55:17Z\",\n" +
        "      \"pushed_at\": \"2022-05-11T16:55:15Z\",\n" +
        "      \"git_url\": \"git://github.com/android/architecture-samples.git\",\n" +
        "      \"ssh_url\": \"git@github.com:android/architecture-samples.git\",\n" +
        "      \"clone_url\": \"https://github.com/android/architecture-samples.git\",\n" +
        "      \"svn_url\": \"https://github.com/android/architecture-samples\",\n" +
        "      \"homepage\": \"\",\n" +
        "      \"size\": 12439,\n" +
        "      \"stargazers_count\": 40720,\n" +
        "      \"watchers_count\": 40720,\n" +
        "      \"language\": \"Kotlin\",\n" +
        "      \"has_issues\": true,\n" +
        "      \"has_projects\": true,\n" +
        "      \"has_downloads\": true,\n" +
        "      \"has_wiki\": true,\n" +
        "      \"has_pages\": false,\n" +
        "      \"forks_count\": 11136,\n" +
        "      \"mirror_url\": null,\n" +
        "      \"archived\": false,\n" +
        "      \"disabled\": false,\n" +
        "      \"open_issues_count\": 210,\n" +
        "      \"license\": {\n" +
        "        \"key\": \"apache-2.0\",\n" +
        "        \"name\": \"Apache License 2.0\",\n" +
        "        \"spdx_id\": \"Apache-2.0\",\n" +
        "        \"url\": \"https://api.github.com/licenses/apache-2.0\",\n" +
        "        \"node_id\": \"MDc6TGljZW5zZTI=\"\n" +
        "      },\n" +
        "      \"allow_forking\": true,\n" +
        "      \"is_template\": false,\n" +
        "      \"topics\": [\n" +
        "        \"android\",\n" +
        "        \"android-architecture\",\n" +
        "        \"samples\"\n" +
        "      ],\n" +
        "      \"visibility\": \"public\",\n" +
        "      \"forks\": 11136,\n" +
        "      \"open_issues\": 210,\n" +
        "      \"watchers\": 40720,\n" +
        "      \"default_branch\": \"main\",\n" +
        "      \"score\": 1.0\n" +
        "    }\n" +
        "  ]\n" +
        "}"

val testResponse = Gson().fromJson(testResponseString, Response::class.java)