'use strict';

angular.module('21pointsApp')
	.controller('GoalDeleteController', function($scope, $modalInstance, entity, Goal) {

        $scope.goal = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Goal.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });