'use strict';

angular.module('21pointsApp')
    .controller('GoalController', function ($scope, $state, $modal, Goal) {
      
        $scope.goals = [];
        $scope.loadAll = function() {
            Goal.query(function(result) {
               $scope.goals = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.goal = {
                name: null,
                description: null,
                id: null
            };
        };
    });
