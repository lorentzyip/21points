'use strict';

angular.module('21pointsApp')
    .controller('EntryController', function ($scope, $state, $modal, Entry, ParseLinks) {
      
        $scope.entrys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Entry.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.entrys.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.entrys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.entry = {
                date: null,
                exercise: null,
                meals: null,
                alcohol: null,
                notes: null,
                id: null
            };
        };
    });
