'use strict';

angular.module('21pointsApp')
    .controller('MetricController', function ($scope, $state, $modal, Metric, ParseLinks) {
      
        $scope.metrics = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Metric.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.metrics = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.metric = {
                name: null,
                amount: null,
                id: null
            };
        };
    });
