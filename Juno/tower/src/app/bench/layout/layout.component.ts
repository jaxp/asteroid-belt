import { AuthService } from '@/app/auth/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  isCollapsed = false;

  constructor(private authService: AuthService) { }

  logout(): void {
    this.authService.logout().subscribe(e => console.log(e));
  }

}
