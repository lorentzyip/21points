'use strict';

angular.module('21pointsApp')
	.controller('MetricDeleteController', function($scope, $modalInstance, entity, Metric) {

        $scope.metric = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Metric.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });