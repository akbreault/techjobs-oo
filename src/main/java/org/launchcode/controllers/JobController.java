package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.lang.model.element.Name;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if (!errors.hasErrors()) {

            String aName = jobForm.getName();
            Employer anEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
            Location aLocation = jobData.getLocations().findById(jobForm.getLocationId());
            PositionType aPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
            CoreCompetency aSkill = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

            Job newJob = new Job(aName, anEmployer, aLocation, aPositionType, aSkill);

            jobData.add(newJob);

            return "redirect:http://localhost:8080/job?id="+newJob.getId();

        }

        return "new-job";

    }
}
