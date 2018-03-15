package com.farmer.async.spider.message;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/17
 */
public abstract class MessageType {

    public static abstract class Cnblog {

        public static abstract class Homepage {

            private static final String CNBLOG_HOMEPAGE_PRE = "cnblog_homepage_";

            public static final String CNBLOG_HOMEPAGE_DOWNLOAD = CNBLOG_HOMEPAGE_PRE + "1";

            public static final String CNBLOG_HOMEPAGE_PARSER = CNBLOG_HOMEPAGE_PRE + "2";

            public static final String CNBLOG_HOMEPAGE_STORAGE = CNBLOG_HOMEPAGE_PRE + "3";

            public static final String CNBLOG_HOMEPAGE_SAVE = CNBLOG_HOMEPAGE_PRE + "4";
        }

        public static abstract class Blogger {

            private static final String CNBLOG_BLOGGER_PRE = "cnblog_blogger_";

            public static final String CNBLOG_BLOGGER_HOME_DOWNLOAD = CNBLOG_BLOGGER_PRE + "1";

            public static final String CNBLOG_BLOGGER_HOME_PARSER = CNBLOG_BLOGGER_PRE + "2";

            public static final String CNBLOG_BLOGGER_DOCUMENT_DOWNLOAD = CNBLOG_BLOGGER_PRE + "3";

            public static final String CNBLOG_BLOGGER_UID_PARSER = CNBLOG_BLOGGER_PRE + "4";

            public static final String CNBLOG_BLOGGER_UID_UPDATE = CNBLOG_BLOGGER_PRE + "5";
        }

        public static abstract class BloggerRelation {

            private static final String CNBLOG_BLOGGER_RELATION_PRE = "cnblog_blogger_relation_";

            public static final String CNBLOG_BLOGGER_RELATION_PAGE_DOWNLOAD = CNBLOG_BLOGGER_RELATION_PRE + 1;

            public static final String CNBLOG_BLOGGER_RELATION_PAGE_PARSER = CNBLOG_BLOGGER_RELATION_PRE + 2;

            public static final String CNBLOG_BLOGGER_RELATION_SAVE = CNBLOG_BLOGGER_RELATION_PRE + 3;

            public static final String CNBLOG_BLOGGER_RELATION_BLOGGER = CNBLOG_BLOGGER_RELATION_PRE + 4;

            public static final String CNBLOG_BLOGGER_RELATION_BLOGGER_LIST = CNBLOG_BLOGGER_RELATION_PRE + 5;
        }
    }
}
