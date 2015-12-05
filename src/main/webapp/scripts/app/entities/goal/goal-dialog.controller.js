'use strict';

angular.module('21pointsApp').controller('GoalDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Goal', 'User',
        function($scope, $stateParams, $modalInstance, entity, Goal, User) {

        $scope.goal = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Goal.get({id : id}, function(result) {
                $scope.goal = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('21pointsApp:goalUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.goal.id != null) {
                Goal.update($scope.goal, onSaveSuccess, onSaveError);
            } else {
                Goal.save($scope.goal, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
