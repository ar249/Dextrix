 public void sendForMissingCertsAndSkills() throws DextrixException {
       List<User> UserIds  = new ArrayList<>();
       UserIds = userDAO.takename();

       if(UserIds.size() == 0){
           throw new DextrixException("No Active Employee");
       }
       List<String> finalMailCert = new ArrayList<>();
       UserIds.forEach(user -> {
           List<Certifications> certifications = new ArrayList<>();
           certifications = certificationsDAO.getEmployeeCertifications(user.getId());
           if(certifications.size() == 0){
               finalMailCert.add(user.getId());
           }
       });
       //   System.out.println("cert :" +finalMailCert);
        EmailContainerDto emailContainerDtoCerts = new EmailContainerDto();
        String reportCertBody;
        LocalDate currentDate = LocalDate.now();
        String reportCertSubject = String.format(emailCertRequiredSubject, currentDate.getMonth());
        emailContainerDtoCerts.setSubject(reportCertSubject);
        emailContainerDtoCerts.setAttachmentIncluded(false);
        reportCertBody = emailCertRequiredTemp;
        emailContainerDtoCerts.setBody(String.format(reportCertBody, currentDate.getMonth()));
        emailContainerDtoCerts.setRecipients(finalMailCert);////
        emailUtil.send(emailContainerDtoCerts);

       // for skills requirement
        List<String> finalMailSkill = new ArrayList<>();
        UserIds.forEach(user -> {
            List<UserSkills> userSkills = new ArrayList<>();
            userSkills = userSkillsDAO.findByUserId(user.getId());
            if(userSkills.size() == 0){
                finalMailSkill.add(user.getId());
            }
        });
        //  System.out.println("skill:  "+ finalMailSkill);
        EmailContainerDto emailContainerDtoSkill = new EmailContainerDto();
        String reportSkillBody;
        String reportSkillSubject = String.format(emailSkillRequiredSubject, currentDate.getMonth());
        emailContainerDtoSkill.setSubject(reportSkillSubject);
        emailContainerDtoSkill.setAttachmentIncluded(false);
        reportSkillBody = emailSkillRequiredTemp;
        emailContainerDtoSkill.setBody(String.format(reportSkillBody, currentDate.getMonth()));
        emailContainerDtoSkill.setRecipients(finalMailSkill);
        emailUtil.send(emailContainerDtoSkill);
    }
