(ns app.core
  (:require
   ["@smooth-ui/core-sc" :refer [Col Grid Normalize Row ThemeProvider]]

   [app.auth.views.log-in :refer [log-in]]
   [app.auth.views.profile :refer [profile]] ;; -- become-a-chef --
   [app.auth.views.sign-up :as sign-up]

   [app.auth.events]
   [app.auth.subs]

   [app.become-a-chef.views.become-a-chef :refer [become-a-chef]] ;; -- inbox --
   [app.db] ;; -- auth --
   [app.inbox.views.inboxes :refer [inboxes]] ;; -- nav --
   [app.nav.events]
   [app.nav.subs] ;; -- recipes --
   [app.nav.views.nav :refer [nav]]
   [app.recipes.views.recipes :refer [recipes]]
   [app.theme :refer [cheffy-theme]]
   [re-frame.core :as rf]
   [reagent.dom :as rd]))

(defn pages
  [page-name]
  (case page-name
    :profile [profile]
    :sign-up [sign-up/sign-up]
    :log-in [log-in]
    :become-a-chef [become-a-chef]
    :inboxes [inboxes]
    :recipes [recipes]
    [recipes]))

(defn app
  []
  (let [active-nav @(rf/subscribe [:active-nav])]
    [:<>
     [:> Normalize]
     [:> ThemeProvider {:theme cheffy-theme}
      [:> Grid {:fluid false}
       [:> Row
        [:> Col
         [nav]
         [pages active-nav]]]]]]))

(defn ^:dev/after-load start
  []
  (rd/render [app]
    (.getElementById js/document "app")))

(defn ^:export init
  []
  (rf/dispatch-sync [:initialize-db])
  (start))
