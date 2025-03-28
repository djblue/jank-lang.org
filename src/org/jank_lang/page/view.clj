(ns org.jank_lang.page.view
  (:require [clojure.string]
            [clojure.java.io :as io]
            [hiccup.page :as page]
            [stringer.core :refer [strcat]]
            [taoensso.timbre :as timbre]))

(defn header [props]
  (let [text-color "has-text-white"]
    [:div
     [:nav {:class (str "navbar " (when (:primary? props)
                                    "is-primary"))}
      [:div {:class "container"}
       [:div {:class "navbar-brand"}
        [:a {:class (str "navbar-item title " text-color)
             :href (:title-url props "/")
             :style {:font-family "Comfortaa"}}
         [:img {:src "/img/logo-transparent.png"
                :style {:margin-right "15px"}}]
         (:title props "jank")]]

       [:div {:class "navbar-menu is-active"}
        [:div {:class "navbar-end has-text-weight-semibold"}
         (when (:home? props true)
           [:a {:class (str "navbar-item " text-color)
                :href "/"}
            [:span {:class "icon mr-1"}
             [:i {:class "gg-home"}]]
            [:strong "Home"]])
         (when (:blog? props true)
           [:a {:class (str "navbar-item " text-color)
                :href "/blog"}
            [:span {:class "icon mr-1"}
             [:i {:class "gg-comment"}]]
            [:strong "Blog"]])
         (when (:sponsor? props false)
           [:a {:class (str "navbar-item " text-color)
                :href "https://github.com/sponsors/jeaye"}
            [:span {:class "icon mr-1"
                    :style "color: rgb(201, 97, 152);"}
             [:i {:class "gg-heart"}]]
            [:strong "Sponsor"]])
         (when (:progress? props true)
           [:a {:class (str "navbar-item " text-color)
                :href "/progress"}
            [:span {:class "icon mr-1"}
             [:i {:class "gg-list"}]]
            [:strong "Progress"]])
         [:a {:class (str "navbar-item " text-color)
              :href "https://github.com/jank-lang/jank"}
          [:span {:class "icon mr-1"}
           [:i {:class "gg-git-fork"}]]
          [:strong "Github"]]
         #_[:a {:class (str "navbar-item " text-color)
                :href "#"}
            [:span {:class "icon mr-1"}
             [:i {:class "gg-info"}]]
            "User Manual"]
         [:a {:class (str "navbar-item " text-color)
              :href "https://clojurians.slack.com/archives/C03SRH97FDK"}
          [:span {:class "icon mr-1"}
           [:i {:class "gg-slack"}]]
          "Slack"]
         [:a {:class (str "navbar-item " text-color)
              :href "https://twitter.com/jeayewilkerson"}
          [:span {:class "icon mr-1"}
           [:i {:class "gg-twitter"}]]
          "Twitter"]]]]]]))

(def css-icons (slurp (io/resource "public/css/icon.css")))

(defn page-root [props & body]
  (page/html5 {}
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet"
            :href "/css/main.css"}]
    [:link {:rel "alternate"
            :type "application/atom+xml"
            :href "/blog/feed.xml"
            :title "RSS Feed"}]
    [:link {:rel "preconnect"
            :href "https://fonts.googleapis.com"}]
    [:link {:rel "preconnect"
            :href "https://fonts.gstatic.com"
            :crossorigin true}]
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css2?family=Fira+Code:wght@300..700&display=swap"}]
    [:title (:title props)]
    [:meta {:property "og:title"
            :content (:title props)}]
    [:meta {:property "og:description"
            :content (clojure.string/replace (:description props "") #"\s*\n\s*" " ")}]
    [:meta {:property "og:image"
            :content (:image props "https://jank-lang.org/img/logo-text-dark.png")}]

    [:link {:rel "apple-touch-icon"
            :sizes "180x180"
            :href "/img/favicon/apple-touch-icon.png"}]
    [:link {:rel "icon"
            :type "image/png"
            :sizes "32x32"
            :href "/img/favicon/32.png"}]
    [:link {:rel "icon"
            :type "image/png"
            :sizes "16x16"
            :href "/img/favicon/16.png"}]
    [:link {:rel "manifest"
            :href "/img/favicon/site.webmanifest"}]

    [:style css-icons]

    "<!-- Matomo -->
    <script>
    var _paq = window._paq = window._paq || [];
    _paq.push(['trackPageView']);
    _paq.push(['enableLinkTracking']);
    (function() {
                 var u=\"//matomo.jeaye.com/\";
                 _paq.push(['setTrackerUrl', u+'matomo.php']);
                 _paq.push(['setSiteId', '1']);
                 var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
                 g.async=true; g.src=u+'matomo.js'; s.parentNode.insertBefore(g,s);
                 })();
    </script>
    <!-- End Matomo Code -->"

    (conj (into [:body] body)
          [:footer {:class "footer"}
           [:div {:class "container"}
            [:div {:class "columns has-text-centered"}
             #_[:div {:class "column is-pulled-right"
                      :style {:margin "auto"}}
                [:div [:img {:src "https://img.shields.io/github/stars/jeaye/jank"
                             :width "100px"}]]
                [:div [:img {:src "https://app.travis-ci.com/jeaye/jank.svg?branch=main"
                             :width "150px"}]]
                [:div [:img {:src "https://codecov.io/gh/jeaye/jank/branch/main/graph/badge.svg"
                             :width "150px"}]]
                [:div [:img {:src "https://img.shields.io/badge/libera%20irc-%23jank-blue"
                             :width "150px"}]]
                ]
             #_[:div {:class "column has-text-centered"}
              [:aside {:class "menu"}
               [:p {:class "menu-label"}
                "Resources"]
               [:ul {:class "menu-list"}
                #_[:li [:a {:href "#"} "User Manual"]]
                [:li [:a {:href "https://clojurians.slack.com/archives/C03SRH97FDK"} "Slack"]]
                [:li [:a {:href "https://github.com/jank-lang/jank"} "Github"]]
                [:li [:a {:href "https://jank-lang.org/blog/feed.xml"} "RSS"]]
                ]
               ]]]
            [:div {:class "container has-text-centered"}
             [:div {:class "content is-small"}
              [:p
               "© 2025 Jeaye Wilkerson | All rights reserved."]]]
            ]])

    "<!-- Matomo noscript -->
    <noscript><p><img src=\"//matomo.jeaye.com/matomo.php?idsite=1&amp;rec=1\" style=\"border:0;\" alt=\"\" /></p></noscript>
    <!-- End matomo noscript -->"

    "<!-- Collapsible tables -->
    <script>
    var coll = document.getElementsByClassName('collapsible');
    var i;

    for(i = 0; i < coll.length; i++) {
      coll[i].addEventListener('click', function() {
        this.classList.toggle('active');
        var content = this.nextElementSibling;
        if(content.style.display === 'block')
        { content.style.display = 'none'; }
        else
        { content.style.display = 'block'; }
      });
    }
    </script>
    <!-- End collapsible tables -->"))
