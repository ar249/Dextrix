public void sendToManager() throws DextrixException {
        List<User> UserIds  = new ArrayList<>();
        UserIds = userDAO.takename();
        List<User> finalMailCert = new ArrayList<>();
        UserIds.forEach(user -> {
            List<Certifications> certifications = new ArrayList<>();
            certifications = certificationsDAO.getEmployeeCertifications(user.getId());
            if(certifications.size() == 0){
                finalMailCert.add(user);
            }
        });

       Set<String> managerIdsCert = new HashSet<>();
       for(User user : finalMailCert){
           managerIdsCert.add(user.getReportingManagerId().getId());
       }

       while(managerIdsCert.size()!=0) {
           ArrayList<User> employeesForManager = new ArrayList<>();
           String managers[] = managerIdsCert.toArray(new String[0]);
           String id = managers[0];
           for (User user : finalMailCert) {
               if (id  ==  user.getReportingManagerId().getId()) {
                   employeesForManager.add(user);
               }
           }
           StringBuilder sb = new StringBuilder();
           for(User user : employeesForManager){
               sb.append(user.getId()+" " );
           }

           List<String> manager = new ArrayList<>();
           manager.add(employeesForManager.get(0).getReportingManagerId().getId());
           // System.out.println(manager + " " + sb.toString());

           EmailContainerDto emailContainerDtoSkill = new EmailContainerDto();
           String reportSkillBody;

           LocalDate currentDate = LocalDate.now();

           String reportSkillSubject = String.format("These Employees have not added any Certificate", currentDate.getMonth());
           emailContainerDtoSkill.setSubject(reportSkillSubject);

           emailContainerDtoSkill.setAttachmentIncluded(false);

           reportSkillBody = sb.toString();
           emailContainerDtoSkill.setBody(String.format(reportSkillBody, currentDate.getMonth()));

           emailContainerDtoSkill.setRecipients(manager);
           emailUtil.send(emailContainerDtoSkill);
           managerIdsCert.remove(employeesForManager.get(0).getReportingManagerId().getId());
       }
