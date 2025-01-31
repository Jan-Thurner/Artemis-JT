import { NgModule } from '@angular/core';
import { ArtemisSharedModule } from 'app/shared/shared.module';
import { ArtemisSidePanelModule } from 'app/shared/side-panel/side-panel.module';
import { ArtemisCoursesRoutingModule } from 'app/overview/courses-routing.module';
import { ArtemisSharedPipesModule } from 'app/shared/pipes/shared-pipes.module';
import { ArtemisExerciseButtonsModule } from 'app/overview/exercise-details/exercise-buttons.module';
import { ArtemisCourseExerciseRowModule } from 'app/overview/course-exercises/course-exercise-row.module';
import { SidebarAccordionComponent } from './sidebar-accordion/sidebar-accordion.component';
import { SidebarCardComponent } from './sidebar-card/sidebar-card.component';
import { SidebarCardItemComponent } from './sidebar-card-item/sidebar-card-item.component';
import { SidebarComponent } from './sidebar.component';
import { ArtemisSharedCommonModule } from '../shared-common.module';
import { SubmissionResultStatusModule } from 'app/overview/submission-result-status.module';

@NgModule({
    imports: [
        ArtemisExerciseButtonsModule,
        ArtemisCourseExerciseRowModule,
        ArtemisSharedModule,
        ArtemisSharedPipesModule,
        ArtemisSidePanelModule,
        ArtemisCoursesRoutingModule,
        ArtemisSharedCommonModule,
        SubmissionResultStatusModule,
    ],
    declarations: [SidebarAccordionComponent, SidebarCardComponent, SidebarCardItemComponent, SidebarComponent],
    exports: [SidebarComponent, SidebarCardComponent],
})
export class ArtemisSidebarModule {}
