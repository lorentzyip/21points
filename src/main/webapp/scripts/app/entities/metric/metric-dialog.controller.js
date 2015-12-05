'use strict';

angular.module('21pointsApp').controller('MetricDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Metric', 'Entry', 'Goal',
        function($scope, $stateParams, $modalInstance, entity, Metric, Entry, Goal) {

        $scope.metric = entity;
        $scope.entrys = Entry.query();
        $scope.goals = Goal.query();
        $scope.load = function(id) {
            Metric.get({id : id}, function(result) {
                $scope.metric = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('21pointsApp:metricUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.metric.id != null) {
                Metric.update($scope.metric, onSaveSuccess, onSaveError);
            } else {
                Metric.save($scope.metric, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
