 public File getAllEmployee(Integer id) throws DextrixException{
        if(id==null){
            LOGGER.error("Error: GET /api/admin/employee-projects/CSV");
            throw new DextrixException("Id can't be null");
        }
         int count = 1;
         List<EmployeeProject> employeeProjectList = employeeProjectDAO.getAllEmployee(id);
         List<String[]> data = new ArrayList<>();
         data.add(new String[]{"Serial no.", "Employee ID" ,"Employee E-Mail","Employee Name","Manager Name","Start Date","End Date","Completed", "Description", "Active"});
         for(EmployeeProject employeeProject : employeeProjectList) {
             data.add(new String[]{String.valueOf(count),
                     employeeProject.getEmpProjectId().toString(),
                     employeeProject.getUserId().getId(),
                     employeeProject.getUserId().getFirstName() + " " + employeeProject.getUserId().getLastName(),
                     employeeProject.getManagerId().getFirstName() + " " + employeeProject.getManagerId().getLastName(),
                     employeeProject.getFromDate(),
                     employeeProject.getToDate(),
                     employeeProject.isCompleted()==1?"Yes":"No",
                     employeeProject.getDescription(),
                     employeeProject.getUserId().getActive()==true?"Yes":"No"
             });
             count++;
         };
        return CSVUtil.writeDataToCSV(data,"EmployeeProjects_" + adminProjectsDAO.findById(id).get().getProjectName() );
    }
