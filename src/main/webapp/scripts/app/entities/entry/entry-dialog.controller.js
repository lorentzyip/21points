'use strict';

angular.module('21pointsApp').controller('EntryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Entry', 'Goal', 'Metric',
        function($scope, $stateParams, $modalInstance, entity, Entry, Goal, Metric) {

        $scope.entry = entity;
        $scope.goals = Goal.query();
        $scope.metrics = Metric.query();
        $scope.load = function(id) {
            Entry.get({id : id}, function(result) {
                $scope.entry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('21pointsApp:entryUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.entry.id != null) {
                Entry.update($scope.entry, onSaveSuccess, onSaveError);
            } else {
                Entry.save($scope.entry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
