var cvmaker = angular.module('CVMaker', [ 'ngRoute' ]);

cvmaker.config([ '$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/signup', {
		templateUrl : 'partials/SignUp.html',
		controller : 'SignUpController'
	}).when('/signin', {
		templateUrl : 'partials/SignIn.html',
		controller : 'SignInController'
	}).when('/NewJobPost', {
		templateUrl : 'partials/NewJobPost.html',
		controller : 'NewJobPostController'
	}).when('/NewCVForm', {
		templateUrl : 'partials/NewCV.html',
		controller : 'CVFormController'
	}).when('/viewTopCV/:id', {
		templateUrl : 'partials/TopProfileCV.html',
		controller : 'ViewEmployeeController'
	}).when('/viewCV/:id', {
		templateUrl : 'partials/EmployeeCV.html',
		controller : 'ViewEmployeeController'
	}).when('/topProfiles', {
		templateUrl : 'partials/TopProfiles.html',
		controller : 'TopProfileController'
	}).when('/viewCV', {
		templateUrl : 'partials/ViewCV.html',
		controller : 'ViewCVController'
	}).when('/searchEmployeeSkills', {
		templateUrl : 'partials/SearchCv.html',
		controller : 'SearchCVController'
	}).when('/ViewJobs', {
		templateUrl : 'partials/ViewJobs.html',
		controller : 'ViewJobsController'
	}).when('/ViewJobsJP/:id', {
		templateUrl : 'partials/ViewJobsJP.html',
		controller : 'ViewJobsJPController'
	}).when('/MyJob', {
		templateUrl : 'partials/ViewMyJob.html',
		controller : 'MyJobController'
	}).when('/ViewMyJobJP/:id', {
		templateUrl : 'partials/ViewMyJobJP.html',
		controller : 'ViewMyJobJPController'
	}).when('/ViewApplicants', {
		templateUrl : 'partials/ViewApplicants.html',
		controller : 'ViewApplicantsController'
	}).when('/ViewApplicantCV/:id', {
		templateUrl : 'partials/ViewApplicantCV.html',
		controller : 'ViewApplicantCVController'
	}).when('/AppliedJobs', {
		templateUrl : 'partials/AppliedJobs.html',
		controller : 'AppliedJobsController'
	}).when('/AppliedJobsJP/:id', {
		templateUrl : 'partials/AppliedJobsJP.html',
		controller : 'AppliedJobsJPController'
	}).when('/uploadResume', {
		templateUrl : 'partials/uploadResume.html',
		controller : 'UploadResume'
	}).when('/SignOut', {
		templateUrl : 'partials/SignUp.html',
		controller : 'SignOutController'		
	}).when('/error', {
		templateUrl : 'partials/error.html'
	}).when('/', {
		templateUrl : 'partials/SignUp.html',
		controller : 'SignUpController'
	}).otherwise({
		redirectTo :'partials/SignUp.html',
		controller : 'SignUpController'
	});
} ]);

var URI="http://localhost:1119/CVMaker/api/";


cvmaker.factory('signFactory', function($location) {
	var factory = {};
	factory.checkIfLoggedIn = function() {
		if(!(localStorage.getItem('userId')!=null)){
			$location.path('\signup');
		}
	};
	factory.signOut = function(){
		localStorage.removeItem('userId');
		localStorage.removeItem('userType');
		$location.path('\signup');
	};	
	return factory;
});

cvmaker.controller('SignUpController', function($scope, $http, $location, $rootScope, signFactory) {
	if(localStorage.getItem('userId')!=null){
		if (localStorage.getItem('userType')==1)
        	$location.path('/NewCVForm');
        else if(localStorage.getItem('userType')==2)
        	$location.path('/NewJobPost');
        else
        	$location.path('/error');
	}
		
	$scope.signup={};
	$scope.signup.email=null;
	$scope.signup.password=null;
	$scope.signup.repassword=null;
	$scope.signup.userType=null;
	$scope.signup.message = null;
	
    $scope.signup.submitForm = function() {
    	
    	$scope.signup.message = null;
    	var data = angular.toJson($scope.signup);
		$http.post(URI + "cvmaker/signUp", data).then(function(response) {
	        $scope.signup.message = response.data.message;
	        $rootScope.userId = response.data.id;
	        $rootScope.userType=response.data.userType;
	        localStorage.setItem('userId',response.data.id);
	        localStorage.setItem('userType',response.data.userType);
	        //alert($rootScope.userType);
	        //cookieFactory.setThecookies($rootScope.userId,$rootScope.userType)
	        if ($rootScope.userType==1)
	        	$location.path('/NewCVForm');
	        else if($rootScope.userType==2)
	        	$location.path('/NewJobPost');
	        else
	        	$location.path('/error');
	    }, function(response) {
	        $scope.signup.message = response.data.message;
	        
	    });
		
    };
    
    $scope.signup.removeEmail=function(){$scope.signup.email=null;};
    $scope.signup.removePassword=function(){$scope.signup.password=null;};
    $scope.signup.removeRepassword=function(){$scope.signup.repassword=null;};
    
});

cvmaker.controller('SignInController', function($scope, $http, $location, $rootScope, signFactory) {
	if(localStorage.getItem('userId')!=null){
		if (localStorage.getItem('userType')==1)
        	$location.path('/ViewJobs');
        else if(localStorage.getItem('userType')==2)
        	$location.path('/NewJobPost');
        else
        	$location.path('/error');
	}
	
	$scope.signin={};
	$scope.signin.message=null;
	$scope.signin.email=null;
	$scope.signin.password=null;
	$scope.signin.userType=null;
	
	$scope.signin.submitForm = function() {
		var data = angular.toJson($scope.signin);
		$http.post(URI + "cvmaker/signin", data).then(function(response) {
	        $scope.signin.message = response.data.message;
	        $rootScope.userId = response.data.id;
	        $rootScope.userType = response.data.userType;
	        //alert("Sign In successful");
	        //cookieFactory.setThecookies($rootScope.userId,$rootScope.userType)
	        localStorage.setItem('userId',response.data.id);
	        localStorage.setItem('userType',response.data.userType);
	        if ($rootScope.userType==1)
	        	$location.path('/ViewJobs');
	        else if($rootScope.userType==2)
	        	$location.path('/NewJobPost');
	        else
	        	$location.path('/error');
	    }, function(response) {
	    	if(response.data.message!=null)
	    		$scope.signin.message = response.data.message;
	    	else
	    		$scope.signin.message="Could not sign in, try again!"
	    });
	};
	
});

cvmaker.controller('CVFormController', function($scope, $http, $location,$rootScope, signFactory) {
	//$ngBootbox.alert("hello");
	signFactory.checkIfLoggedIn();
	$scope.newCV={};
	//alert($rootScope.userId);
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	$scope.newCV.userId=$rootScope.userId;
	$scope.newCV.membership=0;
	
	$http.get(URI+"cvmaker/fetchSkills").then(function(response){
		$scope.skillSet = response.data;
	},function(response){
		alert("Error in fetching skills");
	});
	
	$scope.temp=null;
    $scope.addSkill = function(){
           if($scope.newCV.interests==null){
                  $scope.newCV.interests = $scope.temp;
           }
           else{
                  $scope.newCV.interests = $scope.newCV.interests+$scope.temp;
           }
    };
    
	$http.get(URI+"cvmaker/fetchResume/"+$scope.newCV.userId).then(function(response){
		$scope.newCV.userId=$rootScope.userId;
		$scope.newCV.firstName=response.data.firstName;
		$scope.newCV.lastName=response.data.lastName;
		$scope.newCV.email=response.data.email;
		$scope.newCV.phoneNo=response.data.phoneNo;
		$scope.newCV.experienceInYears=response.data.experienceInYears;
		$scope.newCV.experienceInMonths=response.data.experienceInMonths;
		$scope.newCV.addressLine1=response.data.addressLine1;
		$scope.newCV.addressLine2=response.data.addressLine2;
		$scope.newCV.addressLine3=response.data.addressLine3;
		$scope.newCV.interests=response.data.interests;
		$scope.newCV.membership=response.data.membership;
		$scope.newCV.certifications=response.data.certifications;
		$scope.newCV.reference=response.data.reference;
		$scope.newCV.message=response.data.message;
		$scope.newCV.educationInfoList = response.data.educationInfoList;
		$scope.newCV.workExpList = response.data.workExpList;
		//console.log(typeof $scope.newCV.educationInfoList[0].startDate);
		if($scope.newCV.educationInfoList!=null){
			for (i = 0; i < $scope.newCV.educationInfoList.length; i++) { 
				$scope.newCV.educationInfoList[i].startDate = new Date($scope.newCV.educationInfoList[i].startDate);
				$scope.newCV.educationInfoList[i].endDate = new Date($scope.newCV.educationInfoList[i].endDate);
			}
		}
		if($scope.newCV.workExpList!=null){
			for (i = 0; i < $scope.newCV.workExpList.length; i++) { 
				$scope.newCV.workExpList[i].startDate = new Date($scope.newCV.workExpList[i].startDate);
				$scope.newCV.workExpList[i].endDate = new Date($scope.newCV.workExpList[i].endDate);
			}
		}
		
		//alert(response.data.educationInfoList);
	},function(response){
		//alert(response.data.message);
	});
	
    $scope.newCV.defaultView = function(evt, tabName) {
    
    	    var i, tabcontent, tablinks;

    	    // Get all elements with class="tabcontent" and hide them
    	    tabcontent = document.getElementsByClassName("tabcontent");
    	    for (i = 0; i < tabcontent.length; i++) {
    	        tabcontent[i].style.display = "none";
    	    }

    	    // Get all elements with class="tablinks" and remove the class "active"
    	    tablinks = document.getElementsByClassName("tablinks");
    	    for (i = 0; i < tablinks.length; i++) {
    	        tablinks[i].className = tablinks[i].className.replace(" active", "");
    	    }

    	    // Show the current tab, and add an "active" class to the button that opened the tab
    	    document.getElementById(tabName).style.display = "block";
    	    //evt.currentTarget.className = " active";		
    }; 
    
    $scope.newCV.subscribe=function(){
    	$scope.newCV.membership=1;
    	$scope.newCV.submit();
    }
    
	$scope.newCV.submit = function() {
		$scope.newCV.educationInfoList=[];
		$scope.newCV.workExpList=[];
		
		var myTab = document.getElementById('edutbl');
        for (row = 1; row < myTab.rows.length; row++) {
        	$scope.newCV.educationInfoList[row-1]= {};
            var element = myTab.rows.item(row).cells[0];
            $scope.newCV.educationInfoList[row-1].institute = element.childNodes[0].value;
            element = myTab.rows.item(row).cells[1];
            $scope.newCV.educationInfoList[row-1].course = element.childNodes[0].value;
            element = myTab.rows.item(row).cells[2];
            $scope.newCV.educationInfoList[row-1].startDate = element.childNodes[0].value;
            element = myTab.rows.item(row).cells[3];
            $scope.newCV.educationInfoList[row-1].endDate = element.childNodes[0].value;
            element = myTab.rows.item(row).cells[4];
            $scope.newCV.educationInfoList[row-1].percentage = parseInt(element.childNodes[0].value);
            }
        
        var myTab = document.getElementById('worktbl');
        for (row = 1; row < myTab.rows.length; row++) {
        	$scope.newCV.workExpList[row-1]= {};
            var element = myTab.rows.item(row).cells[0];
            $scope.newCV.workExpList[row-1].jobTitle = element.childNodes[0].value;
            element = myTab.rows.item(row).cells[1];
            $scope.newCV.workExpList[row-1].companyName = element.childNodes[0].value;
            element = myTab.rows.item(row).cells[2];
            $scope.newCV.workExpList[row-1].startDate = element.childNodes[0].value;
            
            element = myTab.rows.item(row).cells[3];
            $scope.newCV.workExpList[row-1].endDate = element.childNodes[0].value;
           
            }
        var data = angular.toJson($scope.newCV);
    	//alert(data);
    	$http.post(URI+"cvmaker/fillForm", data).then(function(response) {
    		//$scope.signup.message = response.data.message;
    		$scope.newCV = response.data;
    		console.log(response);
    		$rootScope.refresh=0;
    		$location.path('/viewCV');
    	    //alert("Data added to database");
	        
	    }, function(response) {
	    	//alert("Negative response");
	        $scope.newCV.message = response.data.message;
	        alert( $scope.newCV.message)
	    });
	};
});

cvmaker.controller('ViewCVController', function($scope, $http, $location,$rootScope,$window, signFactory) {
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	$scope.CV={};
	$scope.CV.userId=$rootScope.userId;
	
	if($rootScope.refresh==0){
		$rootScope.refresh=1;
		$window.location.reload();
	}
	
	$http.get(URI+"cvmaker/fetchResume/"+$rootScope.userId).then(function(response){
		
		$scope.CV.userId=$rootScope.userId;
		$scope.CV.firstName=response.data.firstName;
		$scope.CV.lastName=response.data.lastName;
		$scope.CV.email=response.data.email;
		$scope.CV.phoneNo=response.data.phoneNo;
		$scope.CV.experienceInYears=response.data.experienceInYears;
		$scope.CV.experienceInMonths=response.data.experienceInMonths;
		$scope.CV.addressLine1=response.data.addressLine1;
		$scope.CV.addressLine2=response.data.addressLine2;
		$scope.CV.addressLine3=response.data.addressLine3;
		$scope.CV.interests=response.data.interests;
		$scope.CV.certifications=response.data.certifications;
		$scope.CV.reference=response.data.reference;
		$scope.CV.message=response.data.message;
		$scope.CV.educationInfoList = response.data.educationInfoList;
		$scope.CV.workExpList = response.data.workExpList;
		$scope.CV.resume=response.data.resume;
		if($scope.CV.educationInfoList!=null){
			for (i = 0; i < $scope.CV.educationInfoList.length; i++) { 
				$scope.CV.educationInfoList[i].startDate = new Date($scope.CV.educationInfoList[i].startDate);
				$scope.CV.educationInfoList[i].endDate = new Date($scope.CV.educationInfoList[i].endDate);
			}
		}
		if($scope.CV.workExpList!=null){
			for (i = 0; i < $scope.CV.workExpList.length; i++) { 
				$scope.CV.workExpList[i].startDate = new Date($scope.CV.workExpList[i].startDate);
				$scope.CV.workExpList[i].endDate = new Date($scope.CV.workExpList[i].endDate);
			}
		}
		
		//alert(response.data.educationInfoList);
	},function(response){
		alert(response.data.message);
	});
	 $scope.CV.download = function() {
	    	$http.get(URI+"cvmaker/downloadResume/"+$rootScope.userId).then(function(response){
	    		alert("Pdf downloaded to drive D:");
	    		$location.path('/NewCVForm');
	    		
	    	},function(response){
	    		alert("Error in downloading file");
	    	});
	    };
  
   $scope.CV.edit = function(){
	   $location.path('/NewCVForm');
   };
});

cvmaker.controller('SearchCVController', function($scope, $http, $location,$rootScope, signFactory) {
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.CV={};
	$scope.skills = null;
	$http.get(URI+"cvmaker/fetchResumes/"+$scope.skills).then(function(response){
		$scope.employeeLists=response.data;
	},function(response){
		alert("Error in search controller");
	});
	$scope.search = function(){
		if($scope.skills!=null){
			if($scope.skills.localeCompare("")==0){
				$scope.skills = null;
			}
			else{
				$scope.skills=$scope.skills.toLowerCase();
			}
			
		}
		
		alert("*****"+$scope.skills+"+++++");
		$http.get(URI+"cvmaker/fetchResumes/"+$scope.skills).then(function(response){
			$scope.employeeLists=response.data;
			//alert(response.data.educationInfoList);
		},function(response){
			alert(response.data.message);
		});
	   };
});

cvmaker.controller('ViewEmployeeController', function($scope, $http, $location,$rootScope, $routeParams, signFactory) {

	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.CV={};
	$http.get(URI+"cvmaker/fetchResume/"+$routeParams.id).then(function(response){
		$scope.CV.userId=response.data.userId;
		$scope.CV.firstName=response.data.firstName;
		$scope.CV.lastName=response.data.lastName;
		$scope.CV.email=response.data.email;
		$scope.CV.phoneNo=response.data.phoneNo;
		$scope.CV.experienceInYears=response.data.experienceInYears;
		$scope.CV.experienceInMonths=response.data.experienceInMonths;
		$scope.CV.addressLine1=response.data.addressLine1;
		$scope.CV.addressLine2=response.data.addressLine2;
		$scope.CV.addressLine3=response.data.addressLine3;
		$scope.CV.interests=response.data.interests;
		$scope.CV.certifications=response.data.certifications;
		$scope.CV.reference=response.data.reference;
		$scope.CV.message=response.data.message;
		$scope.CV.educationInfoList = response.data.educationInfoList;
		$scope.CV.workExpList = response.data.workExpList;
		if($scope.CV.educationInfoList!=null){
			for (i = 0; i < $scope.CV.educationInfoList.length; i++) { 
				$scope.CV.educationInfoList[i].startDate = new Date($scope.CV.educationInfoList[i].startDate);
				$scope.CV.educationInfoList[i].endDate = new Date($scope.CV.educationInfoList[i].endDate);
			}
		}
		if($scope.CV.workExpList!=null){
			for (i = 0; i < $scope.CV.workExpList.length; i++) { 
				$scope.CV.workExpList[i].startDate = new Date($scope.CV.workExpList[i].startDate);
				$scope.CV.workExpList[i].endDate = new Date($scope.CV.workExpList[i].endDate);
			}
		}
		//alert(response.data.educationInfoList);
	},function(response){
		alert(response.data.message);
	});
	$scope.CV.closeEmployeeProfile = function(){
		$location.path('/searchEmployeeSkills');
	};
	$scope.CV.closeTopEmployeeProfile = function(){
		$location.path('/topProfiles');
	};
});

cvmaker.controller('TopProfileController', function($scope, $http, $location,$rootScope, signFactory) {
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');

	$scope.skills=null;
	$scope.limit = 5;
	$http.get(URI+"cvmaker/fetchTopResumes/"+$scope.skills).then(function(response){
		$scope.employeeLists=response.data;
		//alert(response.data.educationInfoList);
	},function(response){
		alert(response.data.message);
	});
	$scope.search = function(){
		if($scope.skills!=null){
			if($scope.skills.localeCompare("")==0){
				$scope.skills = null;
			}
			else{
				$scope.skills=$scope.skills.toLowerCase();
			}
			
		}
		$http.get(URI+"cvmaker/fetchTopResumes/"+$scope.skills).then(function(response){
			$scope.employeeLists=response.data;
			//alert(response.data.educationInfoList);
		},function(response){
			alert(response.data.message);
		});
	   };
	  
	   
});

cvmaker.controller('NewJobPostController', function($scope, $http, $location, $rootScope, signFactory) {
	//alert("inside NewJobPostController ");
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$http.get(URI+"cvmaker/fetchSkills").then(function(response){
		$scope.skillSet = response.data;
	},function(response){
		alert("Error in fetching skills");
	});
	
	$scope.newJP={};
	$scope.newJP.jobId = null;
	$scope.newJP.recruiterId=$rootScope.userId;
	$scope.newJP.minExp=null;
	$scope.newJP.location=null;
	$scope.newJP.jobDesc=null;
	$scope.newJP.requiredSkills=null;
	$scope.newJP.role=null;
	$scope.newJP.companyProfile=null;
	$scope.newJP.employmentType=null;
	$scope.newJP.recruiter=null;
	$scope.newJP.contactInfo=null;
	$scope.newJP.requiredSkills=null;
	
	$http.get(URI+"recruiter/fetchCompanyDetails/"+$scope.newJP.recruiterId).then(function(response){
		$scope.newJP.companyProfile=response.data.companyProfile;
		$scope.newJP.contactInfo=response.data.contactInfo;
		alert(response.data);
	},function(response){
		
	});
	
	$scope.temp=null;
    $scope.addSkill = function(){
           if($scope.newJP.requiredSkills == null){
                  $scope.newJP.requiredSkills = $scope.temp;
           }
           else{
                  $scope.newJP.requiredSkills = $scope.newJP.requiredSkills+$scope.temp;
           }
    };

	
    $scope.newJP.submit = function() {
    	$scope.newJP.message = null;
    	var data = angular.toJson($scope.newJP);
		$http.post(URI + "recruiter/newJob", data).then(function(response) {
	        $scope.newJP.message = response.data.message;
	        //$rootScope.userId = response.data.id;
	        //alert("Posted successful");
	    }, function(response) {
	        $scope.newJP.message = response.data.message;
	        
	    });
		
    };
    
});


cvmaker.controller('ViewJobsController', function($scope, $http, $location, $rootScope, signFactory) {
	//alert("inside ViewJobsController ");
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.application={};
	$scope.application.applicantId=$rootScope.userId;
	$scope.application.recruiterId=null;
	$scope.application.jobId=null;
	
	$scope.jobPostList=null;

	$scope.getAllJobs = function(){
		$http.get(URI+"recruiter/fetchAllJobs/"+$scope.application.applicantId).then(function(response){
			$scope.jobPostList=response.data;
			//alert(response.data.jobPostList);
		},function(response){
			alert(response.data.message);
		});
		
	};
	
	
	
	$scope.applyForJob =function(x){
		$scope.application.recruiterId=x.recruiterId;
		$scope.application.jobId=x.jobId;
		var data = angular.toJson($scope.application);
		$http.post(URI + "recruiter/applyForJob", data).then(function(response) {
			$scope.application.message = response.data.message;
	        alert("Applied successful");
	        $scope.getAllJobs();
	    }, function(response) {
	    	alert("You cannot apply for this job as the skills do not match");
	    	$scope.application.message = response.data.message;
	        
	    });
	};
	
	
    
});

cvmaker.controller('MyJobController', function($scope, $http, $location, $rootScope, signFactory) {
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.jobPostList=null;
	$scope.userId=$rootScope.userId;
	$scope.employeeLists=null;
	//alert("your userId is"+$scope.userId);
	$scope.getMyJob = function(){
		$http.get(URI+"recruiter/fetchMyJobs/"+$scope.userId).then(function(response){
			$scope.jobPostList=response.data;
			//alert(response.data.jobPostList);
		},function(response){
			alert(response.data.message);
		});
		
	};
	
	$scope.viewApplicants=function(job){
		$http.get(URI+"recruiter/fetchApplicants/"+job.jobId).then(function(response){
			$rootScope.applicantLists=response.data;
			$location.path('/ViewApplicants');
		},function(response){
			alert(response.data.message);
		});
	};
    
});


cvmaker.controller('AppliedJobsController', function($scope, $http, $location, $rootScope, signFactory) {
	//alert("inside ViewJobsController ");
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.uId=$rootScope.userId;
	$scope.jobPostList=null;
	$scope.getAppliedJobs = function(){
		$http.get(URI+"recruiter/fetchAppliedJobs/"+$scope.uId).then(function(response){
			$scope.jobPostList=response.data;
			//alert(response.data.jobPostList);
		},function(response){
			alert(response.data.message);
		});
		
	};
});


cvmaker.controller('ViewApplicantsController', function($scope, $http, $location, $rootScope, signFactory) {
	//alert("inside ViewJobsController ");
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.uId=$rootScope.userId;
	$scope.employeeLists=$rootScope.applicantLists;
	$scope.closeViewApplicants=function(){
		$location.path('/MyJob');
	}
});

cvmaker.controller('ViewApplicantCVController', function($scope, $http, $location,$rootScope , $routeParams, signFactory) {
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.CV={};
	$http.get(URI+"cvmaker/fetchResume/"+$routeParams.id).then(function(response){
		$scope.CV.userId=response.data.userId;
		$scope.CV.firstName=response.data.firstName;
		$scope.CV.lastName=response.data.lastName;
		$scope.CV.email=response.data.email;
		$scope.CV.phoneNo=response.data.phoneNo;
		$scope.CV.experienceInYears=response.data.experienceInYears;
		$scope.CV.experienceInMonths=response.data.experienceInMonths;
		$scope.CV.addressLine1=response.data.addressLine1;
		$scope.CV.addressLine2=response.data.addressLine2;
		$scope.CV.addressLine3=response.data.addressLine3;
		$scope.CV.interests=response.data.interests;
		$scope.CV.certifications=response.data.certifications;
		$scope.CV.reference=response.data.reference;
		$scope.CV.message=response.data.message;
		$scope.CV.educationInfoList = response.data.educationInfoList;
		$scope.CV.workExpList = response.data.workExpList;
		if($scope.CV.educationInfoList!=null){
			for (i = 0; i < $scope.CV.educationInfoList.length; i++) { 
				$scope.CV.educationInfoList[i].startDate = new Date($scope.CV.educationInfoList[i].startDate);
				$scope.CV.educationInfoList[i].endDate = new Date($scope.CV.educationInfoList[i].endDate);
			}
		}
		if($scope.CV.workExpList!=null){
			for (i = 0; i < $scope.CV.workExpList.length; i++) { 
				$scope.CV.workExpList[i].startDate = new Date($scope.CV.workExpList[i].startDate);
				$scope.CV.workExpList[i].endDate = new Date($scope.CV.workExpList[i].endDate);
			}
		}
		//alert(response.data.educationInfoList);
	},function(response){
		alert(response.data.message);
	});
	$scope.closeApplicantProfile = function(){
		$location.path('/ViewApplicants');
	};
});

cvmaker.controller('SignOutController', function($scope, $http, $location,$rootScope, signFactory) {
	
	//alert("Inside signout");
	signFactory.signOut();
	
	 $location.path('/signup');
});


cvmaker.controller('ViewJobsJPController', function($scope, $http, $location,$rootScope, $routeParams, signFactory) {
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.JP={};
	$http.get(URI+"recruiter/fetchJobPost/"+$routeParams.id).then(function(response){
		$scope.JP.jobId=response.data.jobId;
		$scope.JP.recruiterId=response.data.recruiterId;
		$scope.JP.minExp=response.data.minExp;
		$scope.JP.location=response.data.location;
		$scope.JP.jobDesc=response.data.jobDesc;
		$scope.JP.requiredSkills=response.data.requiredSkills;
		$scope.JP.role=response.data.role;
		$scope.JP.companyProfile=response.data.companyProfile;
		$scope.JP.employmentType=response.data.employmentType;
		$scope.JP.recruiter=response.data.recruiter;
		$scope.JP.contactInfo=response.data.contactInfo;
		$scope.JP.message=response.data.message;
		
	},function(response){
		alert(response.data.message);
	});
  
   $scope.JP.close = function(){
	   $location.path('/ViewJobs');
   };
});


cvmaker.controller('AppliedJobsJPController', function($scope, $http, $location,$rootScope, $routeParams, signFactory) {
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.JP={};
	$http.get(URI+"recruiter/fetchJobPost/"+$routeParams.id).then(function(response){
		$scope.JP.jobId=response.data.jobId;
		$scope.JP.recruiterId=response.data.recruiterId;
		$scope.JP.minExp=response.data.minExp;
		$scope.JP.location=response.data.location;
		$scope.JP.jobDesc=response.data.jobDesc;
		$scope.JP.requiredSkills=response.data.requiredSkills;
		$scope.JP.role=response.data.role;
		$scope.JP.companyProfile=response.data.companyProfile;
		$scope.JP.employmentType=response.data.employmentType;
		$scope.JP.recruiter=response.data.recruiter;
		$scope.JP.contactInfo=response.data.contactInfo;
		$scope.JP.message=response.data.message;
		
	},function(response){
		alert(response.data.message);
	});
  
   $scope.JP.close = function(){
	   $location.path('/AppliedJobs');
   };
});


cvmaker.controller('ViewMyJobJPController', function($scope, $http, $location,$rootScope, $routeParams, signFactory) {
	//alert("Inside ViewMyJobJPController");
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	
	$scope.JP={};
	$http.get(URI+"recruiter/fetchJobPost/"+$routeParams.id).then(function(response){
		$scope.JP.jobId=response.data.jobId;
		$scope.JP.recruiterId=response.data.recruiterId;
		$scope.JP.minExp=response.data.minExp;
		$scope.JP.location=response.data.location;
		$scope.JP.jobDesc=response.data.jobDesc;
		$scope.JP.requiredSkills=response.data.requiredSkills;
		$scope.JP.role=response.data.role;
		$scope.JP.companyProfile=response.data.companyProfile;
		$scope.JP.employmentType=response.data.employmentType;
		$scope.JP.recruiter=response.data.recruiter;
		$scope.JP.contactInfo=response.data.contactInfo;
		$scope.JP.message=response.data.message;
		
	},function(response){
		alert(response.data.message);
	});
  
   $scope.JP.close = function(){
	   $location.path('/MyJob');
   };
});

cvmaker.controller('UploadResume', function($scope, $http,$rootScope,signFactory){
	
	 //ng-if="resume.length>0"
	
	signFactory.checkIfLoggedIn();
	$rootScope.userId=localStorage.getItem('userId');
	$rootScope.userType=localStorage.getItem('userType');
	document.getElementById("user").value = localStorage.getItem('userId');
	document.getElementById("user1").value = localStorage.getItem('userId');
	$scope.resume=null;
	$http.get(URI+"cvmaker/fetchResume/"+$rootScope.userId).then(function(response){
		$scope.resume=response.data.resume;
		if(!($scope.resume!=null)){
			$scope.resume = false;
		}
		//alert($scope.resume.length+"Hi");
	},function(response){
		alert(response.data.message);
	});
	
});