'use strict';

angular.module('21pointsApp')
	.controller('EntryDeleteController', function($scope, $modalInstance, entity, Entry) {

        $scope.entry = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Entry.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });